package com.multiplatform.todoappkmp.domain

import com.multiplatform.todoappkmp.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow
import com.multiplatform.todoappkmp.util.RequestState

interface MongoRepository {
    fun configureTheRealm()
    fun getSelectedTask(taskId: String): ToDoTask?
    fun readActiveTasks(): Flow<RequestState<List<ToDoTask>>>
    fun readCompletedTasks(): Flow<RequestState<List<ToDoTask>>>
    suspend fun addTask(task: ToDoTask)
    suspend fun updateTask(task: ToDoTask)
    suspend fun setCompleted(task: ToDoTask)
    suspend fun setFavorite(task: ToDoTask)
    suspend fun deleteTask(task: ToDoTask)
}