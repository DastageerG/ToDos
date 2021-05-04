package com.example.todos.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todos.R
import com.example.todos.databinding.FragmentListBinding
import com.example.todos.databinding.FragmentUpdateBinding
import com.example.todos.model.Priority
import com.example.todos.model.ToDoData
import com.example.todos.viewmodel.SharedViewModel
import com.example.todos.viewmodel.ToDoViewModel


class UpdateFragment : Fragment()
{
    private var binding: FragmentUpdateBinding? = null
    private val args by navArgs<UpdateFragmentArgs>()

    private val sharedViewModel:SharedViewModel by viewModels()
    private val toDoViewModel:ToDoViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentUpdateBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)

        binding?.spinnerFragmentUpdate?.onItemSelectedListener = sharedViewModel.listener

        binding?.editTextFragmentUpdateTitle?.setText(args.toDoData.title)
        binding?.editTextFragmentUpdateDescription?.setText(args.toDoData.description)
        binding?.spinnerFragmentUpdate?.setSelection(parsePriority(args.toDoData.priority))

        binding?.buttonUpdateFragmentUpdate?.setOnClickListener(View.OnClickListener
        {
            updateToDo()
        })


        return binding?.root
    }

    private fun updateToDo()
    {
        val title = binding?.editTextFragmentUpdateTitle?.text.toString()
        val description = binding?.editTextFragmentUpdateDescription?.text.toString()
        val priority = binding?.spinnerFragmentUpdate?.selectedItem.toString()

        val validatedData = sharedViewModel.verifyData(title,description)

        if(validatedData)
        {
            val toDoData = ToDoData(args.toDoData.id,title,description,sharedViewModel.parsePriority(priority))
            Toast.makeText(context,"ToDo Updated",Toast.LENGTH_SHORT).show()
            toDoViewModel.updateToDo(toDoData)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }
        else
        { Toast.makeText(context,"Empty Field",Toast.LENGTH_SHORT).show() }

    }

    private fun parsePriority(priority: Priority): Int
    {
        return when(priority)
        {
            Priority.High -> 0
            Priority.Medium ->1
            Priority.Low->2
            else -> 0
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_update_fragment,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menuDeleteUpdateFragment-> confirmDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete()
    {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Do you want to Delete '${args.toDoData.title}' ? ")
        dialog.setTitle("Delete ' ${args.toDoData.title} '")
        dialog.setPositiveButton("No")
        {
            _,_ ->
        }
        dialog.setNegativeButton("Yes")
        {
            _,_ ->
            toDoViewModel.deleteToDo(args.toDoData)
            Toast.makeText(context,"ToDo Deleted",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        dialog.show()

    } // confirmDelete


} // Update Fragment class closed