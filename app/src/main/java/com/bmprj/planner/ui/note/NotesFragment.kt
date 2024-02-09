package com.bmprj.planner.ui.note

import android.view.View
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
    private val noteViewModel by viewModels<NotesViewModel> ()
    override fun initView(view: View) {
        initAdapter()
        initLiveDataObservers()
        noteViewModel.getAllNotes()
        binding.addNoteButton.setOnClickListener { addNoteClick() }
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
//            val list = ArrayList<Note>()
//            val a1 = Note("note1","kjdslkjsdflkjsdfkjls","11/02/24 | 13:11")
//            val a2 = Note("note2","*-*-*-*-*-*-*-*-*","00/00/24 | 00:05")
//            list.add(a1)
//            list.add(a2)
//            noteAdapter.updateList(list)
            noteListRecyclerView.adapter=noteAdapter
        }
    }

    private fun addNoteClick(){
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment()
        findNavController().navigate(action)
    }


}