package com.example.bolava.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bolava.R
import com.example.bolava.auth.AuthActivity
import com.example.bolava.auth.AuthViewModel
import com.example.bolava.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment @Inject constructor(): Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewmodel by viewModels<AuthViewModel>()
    private lateinit var authActivity: AuthActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        authActivity = activity as AuthActivity

        setupButtons()
    }

    private fun setupButtons() {
        binding.btnRegister.setOnClickListener {
            if (binding.inputEmail.text.isEmpty()) binding.inputEmail.error =
                resources.getString(R.string.error)
            if (binding.inputPassword.text.isEmpty()) binding.inputPassword.error =
                resources.getString(R.string.error)
            if (binding.inputConfirmPassword.text.isEmpty()) binding.inputConfirmPassword.error =
                resources.getString(R.string.error)
            if (binding.inputEmail.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty() && binding.inputConfirmPassword.text.isNotEmpty()) {
                // Proceed with register authentication
            }
        }

        binding.textLogin.setOnClickListener {
            authActivity.changeFragment()
        }
    }
}