package com.bmprj.planner.ui.note

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseAdapter
import com.bmprj.planner.databinding.NoteListLayoutBinding
import com.bmprj.planner.model.Note
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class NoteAdapter():BaseAdapter<NoteListLayoutBinding, Note>() {
    override val layoutId: Int get() = R.layout.note_list_layout
    private var onItemClicked: ((Note) -> Unit)? = null
    private var onItemSwiped:((Note) -> Unit)? =null

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

    fun setOnSwipedListener(onItemSwiped:(Note)->Unit){
        this.onItemSwiped=onItemSwiped
    }

    fun attachSwipeToDelete(recyclerView: RecyclerView){
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(
            0,ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = list[position]

                list.removeAt(position)
                notifyItemRemoved(position)
                onItemSwiped?.invoke(note)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.icon_delete)
                    .setSwipeLeftActionIconTint(R.color.red)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            }
        }).attachToRecyclerView(recyclerView)


    }
}