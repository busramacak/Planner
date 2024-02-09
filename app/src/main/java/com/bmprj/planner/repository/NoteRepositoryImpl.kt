package com.bmprj.planner.repository


import com.bmprj.planner.data.NoteDao
import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao:NoteDao
) : NoteRepository{
    override suspend fun insertNote(note: Note): Flow<Unit> = flow {
        emit(noteDao.insertNote(note))
    }

    override suspend fun getAllNotes(): Flow<List<Note>> = flow {
        emit(noteDao.getNotes())
    }

    override suspend fun getNote(noteId:Int): Flow<UiState<Note>> =flow {
        emit(noteDao.getNote(noteId))
    }


}