package com.bmprj.planner.ui.note

import androidx.lifecycle.viewModelScope
import com.bmprj.planner.utils.UiState
import com.bmprj.planner.base.BaseViewModel
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task
import com.bmprj.planner.repository.note.NoteRepositoryImpl
import com.bmprj.planner.repository.task.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepositoryImpl: NoteRepositoryImpl,
    private val taskRepositoryImpl: TaskRepositoryImpl
):BaseViewModel() {

    private val _noteList = MutableStateFlow<UiState<List<Note>>>(UiState.Loading)
    val noteList = _noteList.asStateFlow()

    private val _taskList = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val taskList=_taskList.asStateFlow()
    fun getAllNotes() =viewModelScope.launch {
        noteRepositoryImpl.getAllNotes()
            .collect{
                _noteList.emit(UiState.Success(it))
                println(it)
            }
    }

    fun deleteNote(note:Note)= viewModelScope.launch {
        noteRepositoryImpl.deleteNote(note)
            .collect{

        }
    }

    fun getAllTasks()= viewModelScope.launch {
        taskRepositoryImpl.getTasks()
            .collect{
                _taskList.emit(UiState.Success(it))
            }
    }
}