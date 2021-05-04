package com.example.todos.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ToDoTable")
@Parcelize
data class ToDoData
(
        @PrimaryKey(autoGenerate = true)
         var id : Int,
         var title : String,
         var description : String,
         var priority: Priority
        ) : Parcelable

