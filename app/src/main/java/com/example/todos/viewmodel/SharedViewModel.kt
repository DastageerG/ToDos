package com.example.todos.viewmodel

import android.app.Application
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.example.todos.model.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application)
{
    fun verifyData(title: String, description: String): Boolean
    {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
    }

    fun parsePriority(priority: String?): Priority
    {
        return when(priority)
        {
            "High Priority" ->  Priority.High
            "Medium Priority"-> Priority.Medium
            "Low Priority" -> Priority.Low
            else -> Priority.Low
        }

    }


    val listener:AdapterView.OnItemSelectedListener =
            object  : AdapterView.OnItemSelectedListener
            {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
                {
                    when(position)
                    {
                        0 -> (parent?.getChildAt(0) as TextView).setTextColor(Color.RED)
                        1 -> (parent?.getChildAt(0) as TextView).setTextColor(Color.YELLOW)
                        2 -> (parent?.getChildAt(0) as TextView).setTextColor(Color.GREEN)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?)
                {
                    TODO("Not yet implemented")
                }
            }



}