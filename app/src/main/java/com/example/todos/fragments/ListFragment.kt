package com.example.todos.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        // show Message if No Data
        toDoViewModel.getToDosCount()?.observe({lifecycle})
        {
            if(it>0)
            {
                binding?.textViewNoData?.visibility = View.GONE
                binding?.imageViewNoData?.visibility = View.GONE
            } // if closed
            else
            {
                binding?.textViewNoData?.visibility = View.VISIBLE
                binding?.imageViewNoData?.visibility = View.VISIBLE
            } //else closed
        } // getToDosCount Obeserver closed



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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list_fragment,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            // search menu
            R.id.menuListFragSearch -> {}

            // delete All Menu
            R.id.menuListFragDeleteAll->
            {
                toDoViewModel.deleteAllToDos()
                Toast.makeText(context,"All ToDos Deleted",Toast.LENGTH_SHORT).show()
            }
            // Sort by HighPriority Menu
            R.id.menuListFragHighPriority-> {}
            // Sort by LowPriority Menu
            R.id.menuListFragLowPriority-> {}

            // Menu View By Default
            R.id.menuListFragDefault -> {}
            // Menu View by Grid
            R.id.menuListFragGrid -> {}
            // Menu View By List
            R.id.menuListFragList -> {}
        } // when closed
        return super.onOptionsItemSelected(item)
    }



    override fun onDestroyView()
    {
        super.onDestroyView()
        binding = null
    }
} // ListFragment closed