package com.bmprj.planner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task

@Database(entities = [Note::class, Task::class], version = 2)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao

    abstract fun taskDao():TaskDAO

}