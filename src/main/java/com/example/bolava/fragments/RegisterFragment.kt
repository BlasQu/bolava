package com.example.bolava.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import com.example.bolava.R
import com.example.bolava.auth.AuthActivity
import com.example.bolava.auth.AuthViewModel
import com.example.bolava.data.AuthState
import com.example.bolava.data.AuthUser
import com.example.bolava.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment @Inject constructor(): Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewmodel by viewModels<AuthViewModel>()
    private lateinit var authActivity: AuthActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        authActivity = activity as AuthActivity

        setupButtons()
        setupRepositoryToViewModelObserver()

        lifecycleScope.launch {
            setupCollectors()
        }
    }

    private fun setupRepositoryToViewModelObserver() {
        viewmodel.authRepository.state.observe(authActivity, {
            viewmodel.registrationState.value = it
        })
    }

    private suspend fun setupCollectors() {
        viewmodel.registrationState.collect {
            when (it) {
                AuthState.SUCCESS -> {
                    binding.holderProgressBar.holderProgressBar.animate().apply {
                        duration = resources.getInteger(R.integer.shortAnim).toLong()
                        interpolator = FastOutSlowInInterpolator()
                        alpha(0f)
                        binding.holderProgressBar.holderProgressBar.visibility = View.VISIBLE
                    }
                    authActivity.snackbarMessage("The account has been created succesfully.")
                }
                AuthState.LOADING -> {
                    binding.holderProgressBar.holderProgressBar.animate().apply {
                        binding.holderProgressBar.holderProgressBar.visibility = View.VISIBLE
                        duration = resources.getInteger(R.integer.shortAnim).toLong()
                        interpolator = FastOutSlowInInterpolator()
                        alpha(0.8f)
                        }
                    }
                is AuthState.ERROR -> {
                    binding.holderProgressBar.holderProgressBar.animate().apply {
                        duration = resources.getInteger(R.integer.shortAnim).toLong()
                        interpolator = FastOutSlowInInterpolator()
                        alpha(0f)
                        binding.holderProgressBar.holderProgressBar.visibility = View.GONE
                    }
                    authActivity.snackbarMessage(it.exception)
                }
            }
        }
    }

    private fun setupButtons() {
        binding.btnRegister.setOnClickListener {
            if (binding.inputEmail.text.isEmpty()) binding.inputEmail.error =
                resources.getString(R.string.error)
            if (binding.inputPassword.text.isEmpty()) binding.inputPassword.error =
                resources.getString(R.string.error)
            if (binding.inputConfirmPassword.text.isEmpty()) binding.inputConfirmPassword.error =
                resources.getString(R.string.error)
            if (binding.inputEmail.text.isNotEmpty() &&
                binding.inputPassword.text.isNotEmpty() &&
                binding.inputConfirmPassword.text.isNotEmpty() &&
                binding.inputPassword.text.toString() == binding.inputConfirmPassword.text.toString()) {

                viewmodel.registerUser(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())

            } else {
                authActivity.snackbarMessage("Passwords do not match. [ Min. length for passwords is 6 characters. ]")
            }
        }

        binding.textLogin.setOnClickListener {
            authActivity.changeFragment()
        }
    }
}