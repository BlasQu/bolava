package com.example.bolava.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bolava.R
import com.example.bolava.auth.AuthActivity
import com.example.bolava.auth.AuthViewModel
import com.example.bolava.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment @Inject constructor() : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewmodel by viewModels<AuthViewModel>()
    lateinit var authActivity: AuthActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        authActivity = activity as AuthActivity
        setupButtons()

    }

    private fun setupButtons() {
        binding.btnLogin.setOnClickListener {
            if (binding.inputEmail.text.isEmpty()) binding.inputEmail.error =
                resources.getString(R.string.error)
            if (binding.inputPassword.text.isEmpty()) binding.inputPassword.error =
                resources.getString(R.string.error)
            if (binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()) {
                // Proceed with login authentication
            }
        }

        binding.textRegister.setOnClickListener {
            authActivity.changeFragment()
        }
    }
}