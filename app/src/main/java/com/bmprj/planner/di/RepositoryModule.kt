package com.bmprj.planner.di

import com.bmprj.planner.repository.note.NoteRepository
import com.bmprj.planner.repository.note.NoteRepositoryImpl
import com.bmprj.planner.repository.task.TaskRepository
import com.bmprj.planner.repository.task.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindNoteRepository(noteRepositoryImpl:NoteRepositoryImpl):NoteRepository

    @Binds
    @ViewModelScoped
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl):TaskRepository
}