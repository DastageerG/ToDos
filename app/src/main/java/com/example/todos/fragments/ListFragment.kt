package com.example.todos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todos.R
import com.example.todos.databinding.FragmentListBinding
import com.example.todos.fragments.adapter.ToDoListAdapter
import com.example.todos.viewmodel.ToDoViewModel


class ListFragment : Fragment()
{

    private var binding:FragmentListBinding? = null
    private lateinit var toDoAdapter : ToDoListAdapter
    private val toDoViewModel:ToDoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentListBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        toDoAdapter = ToDoListAdapter()

        binding?.recyclerViewListFragment?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding?.recyclerViewListFragment?.adapter = toDoAdapter

        toDoViewModel.getAllToDos()?.observe({lifecycle})
        {
            toDoAdapter.submitList(it)
        }

        binding?.floatingActionButtonListFragment?.setOnClickListener(View.OnClickListener
        {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        })


        return binding?.root
    }


    override fun onDestroyView()
    {
        super.onDestroyView()
        binding = null
    }
} // ListFragment closed