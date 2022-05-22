package com.kl3jvi.pomodroid.model.database

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import com.kl3jvi.pomodroid.model.entities.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDAO) {

    @WorkerThread
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    val allTasksList: Flow<List<Task>> = taskDao.getAllTasks()

    fun getFocusedTask(title: String): List<Task> {
        val query = "SELECT * FROM TASK_TABLE WHERE title = $title"
        val simplequery = SimpleSQLiteQuery(query)
        return taskDao.getFocusTask(simplequery)
    }

    @WorkerThread
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}