package com.bmprj.planner.repository

import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.UiState
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note): Flow<Unit>
    suspend fun getAllNotes():Flow<List<Note>>

    suspend fun getNote(noteId:Int):Flow<Note>
    suspend fun updateNote(note:Note):Flow<Unit>
    suspend fun deleteNote(note:Note):Flow<Unit>

}