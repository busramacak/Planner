package com.bmprj.planner.ui.task

import android.graphics.Canvas
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseAdapter
import com.bmprj.planner.databinding.TaskListLayoutBinding
import com.bmprj.planner.model.Task
import com.bmprj.planner.utils.setDrawable
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class TaskAdapter(
    private var onAlarmClicked:((Task) -> Unit),
    private var onLongClicked:((Task) -> Boolean),
    private var onCompleteClicked:((Task)->Unit),
    private var onItemSwiped:((Task) -> Unit)
):BaseAdapter<TaskListLayoutBinding, Task> (){
    override val layoutId: Int
        get() = R.layout.task_list_layout


    override fun bind(binding: TaskListLayoutBinding, item: Task) {
        with(binding){
            taskList=item
            executePendingBindings()
            alarmIcon.setOnClickListener { onAlarmClicked.invoke(item) }
            root.setOnLongClickListener { onLongClicked.invoke(item) }
            completeTask.setOnClickListener {
                item.isChecked=!item.isChecked
                onCompleteClicked.invoke(item)
                taskTitle.setDrawable(item.isChecked)
            }
        }
    }


    fun attachSwipeToDelete(recyclerView: RecyclerView){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
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
                onItemSwiped.invoke(note)
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