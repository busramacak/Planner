package com.bmprj.planner.ui.addNote

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import com.bmprj.planner.R
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

    private val _charactersLength = MutableStateFlow<Int?>(0)
    val characterLength = _charactersLength.asStateFlow()

    private val _updatedText = MutableStateFlow<String?>("")
    val updatedText = _updatedText.asStateFlow()

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

    fun setUpCharacterCounter(editText:EditText) = viewModelScope.launch{
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                _updatedText.value=p0?.toString()

            }
            override fun afterTextChanged(p0: Editable?) {
                _charactersLength.value=p0?.length
            }
        })
    }
}