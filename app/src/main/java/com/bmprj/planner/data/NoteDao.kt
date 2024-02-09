package com.bmprj.planner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bmprj.planner.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Query("Select * From note")
    suspend fun getNotes():List<Note>
    
    @Query("Select * From note Where noteId")
    suspend fun getNote():Note
}