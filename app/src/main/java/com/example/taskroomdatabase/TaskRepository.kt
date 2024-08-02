package com.example.taskroomdatabase

import java.util.concurrent.Executors

class TaskRepository (private val database: TaskDatabase){

    // Окремий потік
    private val executor = Executors.newSingleThreadExecutor()

    // Операції додавання, видалення і отримання всього списку завдань
    fun getAll() = database.taskDao().getAll()

    fun add(task: Task) {
        executor.execute { database.taskDao().add(task) }
    }

    fun delete(task: Task) {
        executor.execute { database.taskDao().delete(task) }
    }
}