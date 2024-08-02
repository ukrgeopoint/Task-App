package com.example.taskroomdatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class AddTaskFragment : Fragment() {
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.createtask_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        val titleInput: EditText = view.findViewById(R.id.etTitle)
        val descriptionInput: EditText = view.findViewById(R.id.etDescription)
        val addButton: Button = view.findViewById(R.id.btnAdd)

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            viewModel.addTask(title, description)
            parentFragmentManager.popBackStack()
        }

    }
}