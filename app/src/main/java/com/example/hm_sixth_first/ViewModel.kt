package com.example.hm_sixth_first

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel(){
    val taskList: MutableLiveData<List<Model>?> = MutableLiveData()

    init {
        taskList.value = listOf()
    }
    fun addTask(title: String){
        val currentList = taskList.value?.toMutableList() ?: mutableListOf()
        currentList.add(Model(title))
        taskList.value = currentList
    }

    fun markTaskAsDone(position: Int){
        val currentList = taskList.value?.toMutableList()
        currentList?.get(position)?.next = true
        taskList.value = currentList
    }

    fun deleteTask(position: Int){
        val tasks = taskList.value?.toMutableList()
        tasks?.removeAt(position)
        taskList.value = tasks
    }
}