package com.example.taskroomdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel(){
    private val repo = MyApplication.getApp().repo
    private val _listState = MutableLiveData<ListState>(ListState.EmptyList)
    val listState:LiveData<ListState> = _listState
    private val observer = Observer<List<Task>> {
        _listState.postValue(ListState.UpdatedList(list = it))
    }

    init {
        repo.getAll().observeForever(observer)
    }

    fun addTask(title: String, description: String) {
        repo.add(Task(title = title, description = description))
    }

    fun deleteTask(task: Task) {
        repo.delete(task)
    }

    override fun onCleared() {
        repo.getAll().removeObserver(observer)
        super.onCleared()
    }

    sealed class ListState {
        object EmptyList : ListState()
        data class UpdatedList(val list: List<Task>) : ListState()
    }
}