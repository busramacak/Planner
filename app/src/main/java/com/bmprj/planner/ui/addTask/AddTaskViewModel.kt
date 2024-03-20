package com.bmprj.planner.ui.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmprj.planner.model.Task
import com.bmprj.planner.repository.task.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    val taskRepositoryImpl:TaskRepositoryImpl
): ViewModel() {



    fun insertTask(task: Task) = viewModelScope.launch {
        println(task)
    }
}