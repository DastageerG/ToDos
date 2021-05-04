package com.example.todos.repository

import androidx.lifecycle.LiveData
import com.example.todos.data.ToDoDao
import com.example.todos.model.ToDoData

class Repository(private var dao:ToDoDao?)
{


    suspend fun insertToDo(toDoData: ToDoData)
    {
        dao?.insertToDo(toDoData)
    }

    suspend fun updateToDo(toDoData: ToDoData)
    {
        dao?.updateToDo(toDoData)
    }

    suspend fun deleteToDo(toDoData: ToDoData)
    {
        dao?.deleteToDo(toDoData)
    }

    suspend fun deleteAllToDos()
    {
        dao?.deleteAllToDos()
    }


     fun getAllToDos() :LiveData<List<ToDoData>>?
    {
        return dao?.getAllToDos()
    }

    fun countToDos() :LiveData<Int>?
    {
        return dao?.countToDos()
    }


}