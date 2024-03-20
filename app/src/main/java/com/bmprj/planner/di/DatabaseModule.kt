package com.bmprj.planner.di

import android.content.Context
import androidx.room.Room
import com.bmprj.planner.data.NoteDao
import com.bmprj.planner.data.NoteDatabase
import com.bmprj.planner.data.TaskDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DatabaseModule {
    @Provides
    @ViewModelScoped
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao()
    }

    @Provides
    @ViewModelScoped
    fun provideTaskDao(db:NoteDatabase):TaskDAO{
        return db.taskDao()
    }

}