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
import com.chavvarohan.todo.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navControl: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
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

        binding.textViewSignIn.setOnClickListener {
            navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.buttonSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val pass = binding.editTextPassword.text.toString()
            val confirmPass = binding.editTextConfirmPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE

            if (email.isNotEmpty() && pass.trim().isNotEmpty() ) {
                if (pass.length >= 6) {
                    if (pass == confirmPass) {
                        auth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(context,"Registered Successfully",Toast.LENGTH_SHORT).show()
                                    navControl.navigate(R.id.action_signUpFragment_to_homeFragment)
                                } else {
                                    Toast.makeText(
                                        context,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                binding.progressBar.visibility = View.INVISIBLE
                            }
                    } else {
                        Toast.makeText(context, "Password is not matching", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(
                        context, "Password should be greater than 6 characters", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    context, "Enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}