package com.chavvarohan.todo.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chavvarohan.todo.R
import com.chavvarohan.todo.databinding.FragmentHomeBinding
import com.chavvarohan.todo.utils.ToDoAdapter
import com.chavvarohan.todo.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : Fragment(), AddTaskTodoFragment.DialogClickListener,
    ToDoAdapter.ToDoAdapterClickInterface {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navControl: NavController
    private lateinit var popUpFragment: AddTaskTodoFragment
    private lateinit var adapter: ToDoAdapter
    private lateinit var mList: MutableList<ToDoData>
    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        getDataFromFireStore()
        registerEvent()


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getDataFromFireStore() {
        databaseRef.collection("users").get()
            .addOnSuccessListener {
               mList.clear()
                    for (data in it.documents){
                        val user = data.getString("task")?.let { it1 -> ToDoData("it" , it1) }
                        if (user != null){
                            mList.add(user)
                        }
                    }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(context,"Empty tasks",Toast.LENGTH_SHORT).show()
            }
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        databaseRef = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        adapter = ToDoAdapter(mList)
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter

    }

    private fun registerEvent() {
        binding.addTask.setOnClickListener {
            popUpFragment = AddTaskTodoFragment()
            popUpFragment!!.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddTaskTodoFragment"
            )
        }

        binding.buttonLogout.setOnClickListener {
//            auth.signOut()
//            startActivity(
//                Intent(context,SignInFragment::class.java)
//            )
            navControl.navigate(R.id.signInFragment)
        }
        binding.imageRefresh.setOnClickListener {
            navControl.navigate(R.id.homeFragment)
        }
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        val user: MutableMap<String, Any> = HashMap()
        user["task"] = todo
        databaseRef.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(context, "record added successfully", Toast.LENGTH_SHORT).show()
                todoEt.text = null

            }
            .addOnFailureListener {
                Toast.makeText(context, "record failed to add", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDeleteTaskClicked(toDoData: ToDoData) {


        databaseRef.collection("task").document(toDoData.taskId).delete()
//            .addOnCompleteListener{
//            if (it.isSuccessful){
//                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    override fun onEditTaskClicked(toDoData: ToDoData) {
        TODO("Not yet implemented")
    }

}