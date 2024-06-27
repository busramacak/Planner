package com.bmprj.planner.repository.task

import com.bmprj.planner.data.TaskDAO
import com.bmprj.planner.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDAO
) : TaskRepository {
    override suspend fun insertTask(task: Task): Flow<Unit> = flow {
        emit(taskDao.insertTask(task))
    }

    override suspend fun getTasks(): Flow<List<Task>> = flow {
        emit(taskDao.getTasks())
    }

    override suspend fun deleteTask(task: Task): Flow<Unit> = flow {
        emit(taskDao.deleteTask(task))
    }

    override suspend fun updateTask(task: Task): Flow<Unit> = flow {
        emit(taskDao.updateTask(task))
    }
}