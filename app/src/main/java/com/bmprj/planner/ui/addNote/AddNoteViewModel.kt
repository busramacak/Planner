package com.bmprj.planner.ui.addNote

import androidx.lifecycle.viewModelScope
import com.bmprj.planner.base.BaseViewModel
import com.bmprj.planner.data.Entity
import com.bmprj.planner.model.Note
import com.bmprj.planner.repository.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepositoryImpl: NoteRepositoryImpl
):BaseViewModel() {

    private val _insert = MutableStateFlow<Unit>(Unit)
    val insert = _insert.asStateFlow()
    fun insertNote(note: Entity) = viewModelScope.launch {
        noteRepositoryImpl.insertNote(note)
            .collect{
                _insert.emit(it)
            }
    }
}