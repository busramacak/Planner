package com.bmprj.planner.ui.note

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentNotesBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    private val noteAdapter by lazy { NoteAdapter(::onNoteClicked,::onNoteSwiped) }
    private val taskAdapter by lazy { TaskAdapter(::onAlarmClicked,::onTaskLongClicked) }
    private val noteViewModel by viewModels<NotesViewModel> ()
    private var isAllFabVisible=false
    override fun initView(view: View) {

//        binding.instantDate.text=getString(R.string.line,"bu qwoıejkqwopekpoqwkepowqkeşlsads")
//        binding.instantDate.foreground=resources.getDrawable(R.drawable.line)

        noteViewModel.getAllNotes()
        noteViewModel.getAllTasks()
        initLiveDataObservers()
        initAdapter()
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
//        noteAdapter.setOnClickListener { onNoteClicked(it) }
//        noteAdapter.setOnSwipedListener { onNoteSwiped(it) }
//        taskAdapter.setOnAlarmClickListener { onAlarmClicked(it) }
//        taskAdapter.setOnLongClickListener { onTaskLongClicked(it) }
    }

    private fun onTaskLongClicked(it: Task): Boolean {
        println("uzun tiklandi")
        return false
    }


    private fun onAlarmClicked(it: Task) {
        Toast.makeText(requireContext(),it.taskTime,Toast.LENGTH_LONG).show()
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