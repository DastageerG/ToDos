package com.example.todos.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todos.R
import com.example.todos.databinding.FragmentAddBinding
import com.example.todos.databinding.FragmentListBinding
import com.example.todos.model.ToDoData
import com.example.todos.viewmodel.SharedViewModel
import com.example.todos.viewmodel.ToDoViewModel

class AddFragment : Fragment()
{
    val TAG = "1234"
    private var binding: FragmentAddBinding? = null
    private val  sharedViewModel:SharedViewModel by viewModels()
    private val todoViewModel:ToDoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentAddBinding.inflate(inflater,container,false)
        binding?.spinnerFragmentAdd?.onItemSelectedListener = sharedViewModel.listener


        binding?.buttonAddFragmentSave?.setOnClickListener(
                View.OnClickListener
                {
                    saveToDatabase()
                })

        return binding?.root
    }

    private fun saveToDatabase()
    {
        val title = binding?.editTextFragmentAddTitle?.text.toString()
        val description = binding?.editTextFragmentAddDescription?.text.toString()
        val priority = binding?.spinnerFragmentAdd?.selectedItem.toString()
        val validatedData = sharedViewModel.verifyData(title,description)

        if(validatedData)
        {
            val toDoData = ToDoData(0,title,description,sharedViewModel.parsePriority(priority))
            Toast.makeText(context,"ToDo Added",Toast.LENGTH_SHORT).show()
            todoViewModel.insertToDo(toDoData)
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }
        else
        { Toast.makeText(context,"Empty Field",Toast.LENGTH_SHORT).show() }


    }


    override fun onDestroyView()
    {
        super.onDestroyView()
        binding = null
    }

}