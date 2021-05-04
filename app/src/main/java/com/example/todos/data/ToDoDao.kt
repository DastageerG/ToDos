package com.example.todos.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todos.model.ToDoData

@Dao
interface ToDoDao
{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToDo(toDoData: ToDoData)


    @Update
    suspend fun updateToDo(toDoData: ToDoData)

    @Delete
    suspend fun deleteToDo(toDoData: ToDoData)


    @Query("Select * from todotable order by id desc ")
    fun getAllToDos() : LiveData<List<ToDoData>>

}