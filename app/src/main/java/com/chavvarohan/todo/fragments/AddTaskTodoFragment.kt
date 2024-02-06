package com.chavvarohan.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.chavvarohan.todo.R
import com.chavvarohan.todo.databinding.FragmentAddTaskTodoBinding
import com.chavvarohan.todo.utils.ToDoAdapter
import com.google.android.material.textfield.TextInputEditText


class AddTaskTodoFragment : DialogFragment() {

    private lateinit var binding : FragmentAddTaskTodoBinding
    private lateinit var listener : DialogClickListener

    fun setListener(listener : DialogClickListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
    }

    private fun registerEvents() {
        binding.nextButton.setOnClickListener {
            val todoTask = binding.editTextTask.text.toString()
            if (todoTask.isNotEmpty()){
                listener.onSaveTask(todoTask,binding.editTextTask )


            }else{
                Toast.makeText(context,"please type some task",Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageViewClose.setOnClickListener {
            dismiss()

        }

    }

    interface DialogClickListener{
        fun onSaveTask(todo: String, todoEt: TextInputEditText )

    }

}