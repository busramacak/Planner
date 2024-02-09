package com.bmprj.planner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bmprj.planner.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao():NoteDao
}