package com.bmprj.planner.repository.task

import com.bmprj.planner.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insertTask(task: Task): Flow<Unit>

    suspend fun getTasks():Flow<List<Task>>
}