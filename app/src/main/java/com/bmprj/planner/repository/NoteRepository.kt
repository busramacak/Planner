package com.bmprj.planner.repository

import com.bmprj.planner.data.Entity
import com.bmprj.planner.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Entity): Flow<Unit>
    suspend fun getAllNotes():Flow<List<Note>>

}