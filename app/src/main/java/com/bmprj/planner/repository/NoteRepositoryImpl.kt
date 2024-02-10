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

    /** adding new note */
    override suspend fun insertNote(note: Note): Flow<Unit> = flow {
        emit(noteDao.insertNote(note))
    }
    /** get all notes */
    override suspend fun getAllNotes(): Flow<List<Note>> = flow {
        emit(noteDao.getNotes())
    }
    /** gets the note by id */
    override suspend fun getNote(noteId:Int): Flow<Note> =flow {
        emit(noteDao.getNote(noteId))
    }
    /** update note by id */
    override suspend fun updateNote(note: Note): Flow<Unit> =flow{
        emit(noteDao.updateNote(note))
    }


}