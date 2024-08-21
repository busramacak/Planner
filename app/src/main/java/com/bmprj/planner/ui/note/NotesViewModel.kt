package com.bmprj.planner.ui.note

import androidx.lifecycle.viewModelScope
import com.bmprj.planner.utils.UiState
import com.bmprj.planner.base.BaseViewModel
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task
import com.bmprj.planner.repository.note.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
):BaseViewModel() {

    private val _noteList = MutableStateFlow<UiState<List<Note>>>(UiState.Loading)
    val noteList = _noteList.asStateFlow()


    fun getAllNotes() = viewModelScope.launch {
        noteRepository.getAllNotes()
            .collect{
                _noteList.emit(UiState.Success(it))
            }
    }

    fun deleteNote(note:Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
            .collect{
        }
    }


}