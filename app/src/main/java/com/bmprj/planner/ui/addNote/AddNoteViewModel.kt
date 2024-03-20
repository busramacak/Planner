package com.bmprj.planner.ui.addNote

import androidx.lifecycle.viewModelScope
import com.bmprj.planner.base.BaseViewModel
import com.bmprj.planner.model.Note
import com.bmprj.planner.repository.note.NoteRepositoryImpl
import com.bmprj.planner.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepositoryImpl: NoteRepositoryImpl
):BaseViewModel() {

    private val _insert = MutableStateFlow<Unit>(Unit)
    val insert = _insert.asStateFlow()

    private val _note = MutableStateFlow<UiState<Note>>(UiState.Loading)
    val notee = _note.asStateFlow()

    private val _update = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val update = _update.asStateFlow()

    fun insertNote(note: Note) = viewModelScope.launch {
        noteRepositoryImpl.insertNote(note)
            .collect{
                _insert.emit(it)
            }
    }
    fun getNote(noteId:Int) = viewModelScope.launch {
        noteRepositoryImpl.getNote(noteId).collect{
            _note.emit(UiState.Success(it))
        }
    }
    fun updateNote(note:Note) = viewModelScope.launch {
        noteRepositoryImpl.updateNote(note)
            .catch{}
            .collect{
                _update.emit(UiState.Success(it))
            }
    }
}