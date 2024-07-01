package com.bmprj.planner.ui.addNote

import android.text.Editable
import android.text.TextWatcher
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

    private val _insert = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val insert = _insert.asStateFlow()

    private val _note = MutableStateFlow<UiState<Note>>(UiState.Loading)
    val notee = _note.asStateFlow()

    private val _update = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val updatee = _update.asStateFlow()

    private val _charactersLength = MutableStateFlow<Int?>(0)
    val characterLength = _charactersLength.asStateFlow()

    private val _textState = MutableStateFlow<String>("")
    val textState = _textState.asStateFlow()

    private val undoList = mutableListOf<String>()
    private val _redoList = mutableListOf<String>()
    val redoList get() = _redoList


    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            p0?.let {
                updateText(it.toString())
            }
        }
        override fun afterTextChanged(p0: Editable?) {
            _charactersLength.value=p0?.length
        }
    }
    fun insertNote(note: Note) = viewModelScope.launch {
        noteRepositoryImpl.insertNote(note)
            .collect{
                _insert.emit(UiState.Success(it))
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

    private fun updateText(newText:String){
        val currentText = _textState.value

        if(newText != currentText){
            undoList.add(currentText)
            _textState.value=newText
            _redoList.clear()
        }
    }

    fun undo(){
        if(undoList.isNotEmpty()){
            val prevText = undoList.removeAt(undoList.size-1)
            _redoList.add(_textState.value)
            _textState.value=prevText
        }
    }

    fun redo(){
        if(_redoList.isNotEmpty()){
            val nextText = _redoList.removeAt(_redoList.size-1)
            undoList.add(_textState.value)
            _textState.value=nextText
        }
    }
}