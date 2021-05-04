package com.example.todos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todos.data.DatabaseHelper
import com.example.todos.data.ToDoDao
import com.example.todos.model.ToDoData
import com.example.todos.repository.Repository
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application)
{

    private val noDoDao:ToDoDao? = DatabaseHelper.getInstance(application.applicationContext)?.toDoDao()
    private val repository:Repository = Repository(noDoDao)


    fun getAllToDos() : LiveData<List<ToDoData>>?
    {
        return repository.getAllToDos()
    }

    fun insertToDo(toDoData: ToDoData)
    {
        viewModelScope.launch()
        {
            repository.insertToDo(toDoData)
        }
    } // insertToDo closed


    fun deleteToDo(toDoData: ToDoData)
    {
        viewModelScope.launch()
        {
            repository.deleteToDo(toDoData)
        }
    } // insertToDo closed



    fun updateToDo(toDoData: ToDoData)
    {
        viewModelScope.launch()
        {
            repository.updateToDo(toDoData)
        }
    } // updateTodo closed






}