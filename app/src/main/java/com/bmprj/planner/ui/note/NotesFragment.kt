package com.bmprj.planner.ui.note

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentNotesBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task
import com.bmprj.planner.ui.task.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    private val noteAdapter by lazy { NoteAdapter(::onNoteClicked,::onNoteSwiped) }
    private val noteViewModel by viewModels<NotesViewModel> ()

    override fun initView(view: View) {
        noteViewModel.getAllNotes()
        initLiveDataObservers()
        initAdapter()
        with(binding){
            addNoteButton.setOnClickListener { addClick() }
        }
    }

    private fun initLiveDataObservers() {
        noteViewModel.noteList.handleState(
            onLoading = {},
            onError = {},
            onSucces = {
                noteAdapter.updateList(it)
            }
        )
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
        noteViewModel.deleteNote(it)
    }

    private fun onNoteClicked(note: Note) {
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(note.noteId)
        findNavController().navigate(action)

    }

    private fun addClick(){
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(0)
        findNavController().navigate(action)
    }
}