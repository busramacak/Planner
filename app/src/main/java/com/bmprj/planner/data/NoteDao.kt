package com.bmprj.planner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bmprj.planner.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Query("Select * From note")
    suspend fun getNotes():List<Note>
    @Query("Select * From note Where noteId=:noteId")
    suspend fun getNote(noteId:Int):Note
    @Update
    suspend fun updateNote(note:Note)
    @Delete()
    suspend fun deleteNote(note:Note)
}