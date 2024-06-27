package com.bmprj.planner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bmprj.planner.model.Note
import com.bmprj.planner.model.Task

@Database(entities = [Note::class, Task::class], version = 6)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao

    abstract fun taskDao():TaskDAO

}