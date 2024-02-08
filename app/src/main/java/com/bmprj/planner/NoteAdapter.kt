package com.bmprj.planner

import com.bmprj.planner.base.BaseAdapter
import com.bmprj.planner.databinding.NoteListLayoutBinding
import com.bmprj.planner.model.Note


class NoteAdapter():BaseAdapter<NoteListLayoutBinding,Note>() {
    override val layoutId: Int get() = R.layout.note_list_layout

    override fun bind(binding: NoteListLayoutBinding, item: Note) {
        with(binding){
            noteList=item
            executePendingBindings()
        }
    }
}