package com.example.todos.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todos.R
import com.example.todos.databinding.FragmentListBinding
import com.example.todos.fragments.adapter.ToDoListAdapter
import com.example.todos.model.ToDoData
import com.example.todos.viewmodel.ToDoViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment() , SearchView.OnQueryTextListener
{

    private var binding:FragmentListBinding? = null
    private lateinit var toDoAdapter : ToDoListAdapter
    private val toDoViewModel:ToDoViewModel by viewModels()
    private lateinit var toDoList:List<ToDoData>

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
        } // getToDosCount Observer closed



        setupRecyclerView(binding?.recyclerViewListFragment)
        toDoViewModel.getAllToDos()?.observe({lifecycle})
        {
            toDoAdapter.submitList(it)
            toDoList = it
        }

        binding?.floatingActionButtonListFragment?.setOnClickListener(View.OnClickListener
        {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        })


        return binding?.root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?)
    {
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.adapter = toDoAdapter
        recyclerView?.itemAnimator = SlideInUpAnimator().apply { addDuration = 350 }

        // add Swipe to Delete feature to RecyclerView
        setupSwipeToDelete(recyclerView)

    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView?)
    {
        val swipeToDelete:ItemTouchHelper.SimpleCallback =
                object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT)
        {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
            {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                // method used as undo After swiped
                restoreItem(viewHolder.itemView,toDoList.get(viewHolder.adapterPosition),viewHolder.adapterPosition)

                toDoViewModel.deleteToDo(toDoList.get(viewHolder.adapterPosition))
                toDoAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            } // onSwiped closed

        } // swipeToDelete Object closed

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    } // setupSwipeToDelete method closed

    private fun restoreItem(view: View, todoData: ToDoData, adapterPosition: Int)
    {
        // show a snackBar to Undo deletion
        val snackbar = Snackbar.make(view,"Deleted '${todoData.title}'",Snackbar.LENGTH_LONG)
                .setAction("Undo")
                {
                   toDoViewModel.insertToDo(todoData)
                    toDoAdapter.notifyItemChanged(adapterPosition)
                }
            snackbar.show()
    } // RestoreItem closed


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list_fragment,menu)
        // Search Menu
        val search:MenuItem = menu.findItem(R.id.menuListFragSearch)
        val searchView:SearchView?  = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    } // onCreate optionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            // delete All Menu
            R.id.menuListFragDeleteAll->
            {
                // if there are toDos Available then delete else not
                toDoViewModel.getToDosCount()?.observe({lifecycle})
                {
                    if(it>0)
                    {
                        toDoViewModel.deleteAllToDos()
                        Toast.makeText(context,"$it ToDos Deleted",Toast.LENGTH_SHORT).show()
                    }
                    else if(it==0)
                    {
                        Toast.makeText(context,"No ToDos AvailAble",Toast.LENGTH_SHORT).show()
                    }
                } // observer closed

            }
            // Sort by HighPriority Menu
            R.id.menuListFragHighPriority-> toDoViewModel.getToDosWithHighPrior()?.observe({lifecycle}) {toDoAdapter.submitList(it)}

            // Sort by LowPriority Menu
            R.id.menuListFragLowPriority-> toDoViewModel.getToDosWithLowPrior()?.observe({lifecycle}) {toDoAdapter.submitList(it)}

        } // when closed
        return super.onOptionsItemSelected(item)
    } // onOptionsItemsItemSelected closed




    override fun onDestroyView()
    {
        super.onDestroyView()
        binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean
    {

      

        if(query!=null)
        {
            searchFromDatabase(query)
        }
        return  true
    } // onQueryTexSubmit closed


    override fun onQueryTextChange(query: String?): Boolean
    {


        if(query!=null)
        {
            searchFromDatabase(query)
        }
        return true
    } // OnQueryTextChange closed



    private fun searchFromDatabase(query: String)
    {



        val searchQuery = "%$query%"
        toDoViewModel.getToDosWithSearchQuery(searchQuery)?.observe({lifecycle})
        {
            list->
            list?.let ()
            {
                toDoAdapter.submitList(it)
                Log.d("1234", "searchFromDatabase: "+it)
            } //
        } // observer closed
    } // searchFromDatabase closed

} // ListFragment closed