package com.example.taskroomdatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
        val listView: RecyclerView = view.findViewById(R.id.list)
        val fab: FloatingActionButton = view.findViewById(R.id.fabButton)

        listView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TaskListAdapter()
        listView.adapter = adapter

        viewModel.listState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is TaskViewModel.ListState.EmptyList -> Unit
                is TaskViewModel.ListState.UpdatedList -> {
                    adapter.updateItems(uiState.list)
                }
            }
        }

        // Create new task
        fab.setOnClickListener {
            val fragment = AddTaskFragment()
            parentFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
        }

        // Swipe for delete
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.END)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    viewModel.deleteTask(adapter.items[viewHolder.adapterPosition])
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(listView)

    }
}