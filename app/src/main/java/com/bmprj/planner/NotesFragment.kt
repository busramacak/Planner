package com.bmprj.planner

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentNotesBinding
import com.bmprj.planner.model.Note


class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    private val noteAdapter by lazy { NoteAdapter()}
    override fun initView(view: View) {
        initAdapter()
        binding.addNoteButton.setOnClickListener { addNoteClick() }
    }

    private fun initAdapter(){
        with(binding){
            noteListRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            val list = ArrayList<Note>()
            val a1 = Note("note1","kjdslkjsdflkjsdfkjls","11/02/24 | 13:11")
            val a2 = Note("note2","*-*-*-*-*-*-*-*-*","00/00/24 | 00:05")
            list.add(a1)
            list.add(a2)
            noteAdapter.updateList(list)
            noteListRecyclerView.adapter=noteAdapter
        }
    }

    private fun addNoteClick(){
        val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment()
        findNavController().navigate(action)
    }


}