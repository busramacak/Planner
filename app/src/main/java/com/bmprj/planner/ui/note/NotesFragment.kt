package com.bmprj.planner.ui.note

import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentNotesBinding
import com.bmprj.planner.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    private val noteAdapter by lazy { NoteAdapter() }
    private val taskAdapter by lazy { TaskAdapter() }
    private val noteViewModel by viewModels<NotesViewModel> ()
    private var isAllFabVisible=false
    override fun initView(view: View) {

//        binding.instantDate.text=getString(R.string.line,"bu qwoıejkqwopekpoqwkepowqkeşlsads")
//        binding.instantDate.foreground=resources.getDrawable(R.drawable.line)
        initAdapter()
        initLiveDataObservers()
        noteViewModel.getAllNotes()
        noteViewModel.getAllTasks()
        with(binding){
            addButton.setOnClickListener { addClick() }
            addNoteButton.setOnClickListener { addNoteClick() }
            addTaskButton.setOnClickListener { addTaskClick() }
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

        noteViewModel.taskList.handleState(
            onSucces = {
                taskAdapter.updateList(it)
            }
        )
    }
 
    private fun initAdapter(){
        with(binding){
            noteListRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            noteListRecyclerView.adapter=noteAdapter

            noteAdapter.attachSwipeToDelete(noteListRecyclerView)

            taskListRecyclerView.layoutManager =LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            taskListRecyclerView.adapter=taskAdapter
        }
        noteAdapter.setOnClickListener { onNoteClicked(it) }
        noteAdapter.setOnSwipedListener { onNoteSwiped(it) }

    }

    private fun onNoteSwiped(it: Note) {
        noteViewModel.deleteNote(it)
    }

    private fun onNoteClicked(note: Note) {
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(note.noteId)
        findNavController().navigate(action)

    }

    private fun addClick(){
        with(binding) {
            if (!isAllFabVisible) {
                addNoteButton.show()
                addNoteText.visibility = View.VISIBLE
                addTaskButton.show()
                addTaskText.visibility = View.VISIBLE
                isAllFabVisible = true

            } else {
                addNoteButton.hide()
                addNoteText.visibility = View.GONE
                addTaskButton.hide()
                addTaskText.visibility = View.GONE
                isAllFabVisible = false
            }
        }
    }

    private fun addNoteClick() {
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(0)
        findNavController().navigate(action)
    }

    private fun addTaskClick() {
        val action = NotesFragmentDirections.actionNotesFragmentToAddTaskFragment()
        findNavController().navigate(action)
    }

}