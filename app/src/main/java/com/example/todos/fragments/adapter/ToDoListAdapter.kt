package com.example.todos.fragments.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.databinding.LayoutTodoItemsBinding
import com.example.todos.fragments.ListFragmentDirections
import com.example.todos.model.Priority
import com.example.todos.model.ToDoData

class ToDoListAdapter : androidx.recyclerview.widget.ListAdapter<ToDoData,ToDoListAdapter.ViewHolder>
(
        object :DiffUtil.ItemCallback<ToDoData>()
        {
            override fun areItemsTheSame(oldItem: ToDoData, newItem: ToDoData): Boolean
            {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ToDoData, newItem: ToDoData): Boolean
            {
                return oldItem.toString() == newItem.toString()
            }
        }
        )
{

    inner class ViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_todo_items,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        LayoutTodoItemsBinding.bind(holder.itemView).apply()
        {
            val todo = getItem(position)

            textViewLayoutToDoItemsTitle.text = todo.title
            textViewLayoutToDoItemsDescription.text = todo.description

            when(todo.priority)
            {
                Priority.High -> layoutToDoItemsPriorityIndicator.setCardBackgroundColor(Color.RED)
                Priority.Medium-> layoutToDoItemsPriorityIndicator.setCardBackgroundColor(Color.YELLOW)
                Priority.Low -> layoutToDoItemsPriorityIndicator.setCardBackgroundColor(Color.GREEN)


            } // when closed


        } // binding closed

        holder.itemView.setOnClickListener(View.OnClickListener
        {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(getItem(position))
            holder.itemView.findNavController().navigate(action)
        })


    } // onBindViewHolder closed

} // ToDOListAdapter class closed