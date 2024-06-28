package com.bmprj.planner.ui.task

import com.bmprj.planner.R
import com.bmprj.planner.base.BaseAdapter
import com.bmprj.planner.databinding.TaskListLayoutBinding
import com.bmprj.planner.model.Task
import com.bmprj.planner.utils.setDrawable
class TaskAdapter(
    private var onAlarmClicked:((Task) -> Unit),
    private var onLongClicked:((Task) -> Boolean),
    private var onCompleteClicked:((Task)->Unit),
    private var onDeleteClicked:((Task) -> Unit)
):BaseAdapter<TaskListLayoutBinding, Task> (){
    override val layoutId: Int
        get() = R.layout.task_list_layout


    override fun bind(binding: TaskListLayoutBinding, item: Task) {
        with(binding){
            taskList=item
            executePendingBindings()
            alarmIcon.setOnClickListener { onAlarmClicked.invoke(item) }
            deleteIcon.setOnClickListener { onDeleteClicked.invoke(item) }
            root.setOnLongClickListener { onLongClicked.invoke(item) }
            completeTask.setOnClickListener {
                item.isChecked=!item.isChecked
                onCompleteClicked.invoke(item)
                taskTitle.setDrawable(item.isChecked)
            }
        }
    }


}