package com.example.taskroomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "task_database")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String
)

@Dao
interface TaskDao {
    @Insert
    fun add(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_database")
    fun getAll(): LiveData<List<Task>>
}