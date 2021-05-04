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


    fun getToDosWithHighPrior() :LiveData<List<ToDoData>>?
    {
        return dao?.getToDosWithHighPrior()
    }


    fun getToDosWithLowPrior() :LiveData<List<ToDoData>>?
    {
        return dao?.getToDosWithLowPrior()
    }


    fun getToDosWithSearchQuery(query:String) :LiveData<List<ToDoData>>?
    {
        return dao?.getToDosWithSearchQuery(query)
    }




    fun countToDos() :LiveData<Int>?
    {
        return dao?.countToDos()
    }


}