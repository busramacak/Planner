package com.bmprj.planner.ui.note

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentNotesBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task
import com.bmprj.planner.ui.MainViewModel
import com.bmprj.planner.ui.task.TaskAdapter
import com.bmprj.planner.utils.makeDialog
import com.bmprj.planner.utils.makeDialogForDelete
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    private val noteAdapter by lazy { NoteAdapter(::onNoteClicked,::onNoteSwiped) }
    private val noteViewModel by viewModels<NotesViewModel> ()
    private val sharedViewModel by activityViewModels<MainViewModel>()
    override fun initView(view: View) {
        noteViewModel.getAllNotes()
        initLiveDataObservers()
        initAdapter()
    }

    private fun initLiveDataObservers() {
        noteViewModel.noteList.handleState(
            onLoading = {},
            onError = {},
            onSucces = {
                noteAdapter.updateList(it)
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.fabClickEvent.collect { isClicked ->
                if (isClicked) {
                    addNoteClick()
                    sharedViewModel.makeFabValueDefault()
                }
            }
        }
    }
 
    private fun initAdapter(){
        with(binding){
            noteListRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            noteListRecyclerView.adapter=noteAdapter

            noteAdapter.attachSwipeToDelete(noteListRecyclerView)
        }
    }

    private fun onNoteSwiped(it: Note) {

        val dialog = makeDialogForDelete()
        dialog.setPositiveButton("OK"){_,_ ->
            noteAdapter.list.remove(it)
            noteAdapter.notifyDataSetChanged()
            noteViewModel.deleteNote(it)
        }
        dialog.setNegativeButton("NO"){_,_ ->
            noteAdapter.notifyDataSetChanged()
        }
        dialog.show()

    }

    private fun onNoteClicked(note: Note) {
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(note.noteId)
        findNavController().navigate(action)

    }

    private fun addNoteClick(){
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(0)
        findNavController().navigate(action)
    }
}