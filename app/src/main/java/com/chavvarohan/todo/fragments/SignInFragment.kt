package com.chavvarohan.todo.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.chavvarohan.todo.R
import com.chavvarohan.todo.databinding.FragmentSignInBinding
import com.chavvarohan.todo.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var navControl: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }



    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun registerEvents() {

        binding.textViewSignUp.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.buttonSignIn.setOnClickListener {

            val email = binding.editTextEmail.text.toString()
            val pass = binding.editTextPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE

            if (email.isNotEmpty() && pass.trim().isNotEmpty() ) {
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context,"Login Successfully",Toast.LENGTH_SHORT).show()
                        navControl.navigate(R.id.action_signInFragment_to_homeFragment)

                    } else {
                        Toast.makeText(context,"Incorrect email or password", Toast.LENGTH_SHORT).show()
                    }
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }else{
                Toast.makeText(context,"Empty fields not allowed",Toast.LENGTH_SHORT).show()
            }
        }
    }



}