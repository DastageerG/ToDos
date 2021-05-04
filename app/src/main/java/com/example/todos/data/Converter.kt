package com.example.todos.data

import androidx.room.TypeConverter
import com.example.todos.model.Priority

class Converter
{

    @TypeConverter
    fun fromPriority(priority: Priority) : String
    {
        return priority.name
    }

    @TypeConverter
    fun fromString(string: String):Priority
    {
        return Priority.valueOf(string)
    }
}
