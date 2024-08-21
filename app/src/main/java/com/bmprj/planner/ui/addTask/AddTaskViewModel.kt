package com.bmprj.planner.ui.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmprj.planner.model.Task
import com.bmprj.planner.repository.task.TaskRepository
import com.bmprj.planner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    val taskRepository: TaskRepository
): ViewModel() {


    private val _task = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val task = _task.asStateFlow()
    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
            .catch {
                println(it.message)
            }
            .collect{
                println("oldi")
            }

    }

    fun getTasks() = viewModelScope.launch {


        taskRepository.getTasks()
            .onStart {
                _task.emit(UiState.Loading)
            }
            .catch {
                _task.emit(UiState.Error(it))
            }
            .collect{
                _task.emit(UiState.Success(it))
            }
    }
}