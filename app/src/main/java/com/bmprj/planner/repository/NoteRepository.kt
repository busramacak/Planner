package com.bmprj.planner.repository

import com.bmprj.planner.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note): Flow<Unit>
    suspend fun getAllNotes():Flow<List<Note>>

    suspend fun getNote():Flow<Note>

}