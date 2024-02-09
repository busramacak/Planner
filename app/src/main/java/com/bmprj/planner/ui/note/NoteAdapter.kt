package com.bmprj.planner.ui.note

import com.bmprj.planner.R
import com.bmprj.planner.base.BaseAdapter
import com.bmprj.planner.databinding.NoteListLayoutBinding
import com.bmprj.planner.model.Note


class NoteAdapter():BaseAdapter<NoteListLayoutBinding, Note>() {
    override val layoutId: Int get() = R.layout.note_list_layout
    private var onItemClicked: ((Note) -> Unit)? = null

    override fun bind(binding: NoteListLayoutBinding, item: Note) {
        with(binding) {
            noteList = item
            executePendingBindings()
            root.setOnClickListener { onItemClicked?.invoke(item) }

        }
    }
    fun setOnClickListener(onItemClicked:(Note)-> Unit){
        this.onItemClicked=onItemClicked
    }
}