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


    @Query("delete from todotable ")
    suspend fun deleteAllToDos()

    @Query("Select * from todotable order by id desc ")
    fun getAllToDos() : LiveData<List<ToDoData>>

    @Query("Select * from todotable order by case when priority like 'H%' then 1  when priority like 'M%' then 2  when priority like 'L%' then 3 end   ")
    fun getToDosWithHighPrior() : LiveData<List<ToDoData>>

    @Query("Select * from todotable order by case when priority like 'L%' then 1  when priority like 'M%' then 2  when priority like 'H%' then 3 end   ")
    fun getToDosWithLowPrior() : LiveData<List<ToDoData>>

    @Query("Select * from todotable where title like :query")
    fun getToDosWithSearchQuery(query: String) : LiveData<List<ToDoData>>


    @Query("Select Count(*) from todotable ")
    fun countToDos() : LiveData<Int>

}