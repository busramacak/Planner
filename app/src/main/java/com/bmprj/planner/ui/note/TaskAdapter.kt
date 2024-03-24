package com.bmprj.planner.ui.note

import com.bmprj.planner.R
import com.bmprj.planner.base.BaseAdapter
import com.bmprj.planner.databinding.TaskListLayoutBinding
import com.bmprj.planner.model.Task

class TaskAdapter:BaseAdapter<TaskListLayoutBinding, Task> (){
    override val layoutId: Int
        get() = R.layout.task_list_layout
    private var onAlarmClicked:((Task) -> Unit)?=null
    private var onLongClicked:((Task) -> Boolean)?=null

    override fun bind(binding: TaskListLayoutBinding, item: Task) {
        with(binding){
            taskList=item
            executePendingBindings()
            alarmIcon.setOnClickListener { onAlarmClicked?.invoke(item) }
            root.setOnLongClickListener { onLongClicked?.invoke(item) == false }
        }
    }

    fun setOnLongClickListener(onLongClicked:(Task) -> Boolean){
        this.onLongClicked=onLongClicked
    }
    fun setOnAlarmClickListener(onAlarmClicked:(Task) -> Unit){
        this.onAlarmClicked=onAlarmClicked
    }
}