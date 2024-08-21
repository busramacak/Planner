package com.bmprj.planner.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmprj.planner.model.Task
import com.bmprj.planner.repository.task.TaskRepository
import com.bmprj.planner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
):ViewModel() {

    private val _taskList = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val taskList=_taskList.asStateFlow()

    fun getAllTasks() = viewModelScope.launch {
        taskRepository.getTasks()
            .collect{
                _taskList.emit(UiState.Success(it))
            }
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
            .collect{

            }
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task).collect {

        }
    }
}