package com.kl3jvi.pomodroid.model.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import androidx.sqlite.db.SupportSQLiteQuery
import com.kl3jvi.pomodroid.model.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM TASK_TABLE ORDER BY ID")
    fun getAllTasks(): List<Task>

    @RawQuery
    fun getFocusTask(query: SupportSQLiteQuery) : List<Task>

    @Delete
    suspend fun deleteTask(task: Task)

}