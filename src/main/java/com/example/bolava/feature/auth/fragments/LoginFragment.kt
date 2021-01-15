package com.example.bolava.feature.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import com.example.bolava.R
import com.example.bolava.feature.auth.AuthActivity
import com.example.bolava.feature.auth.AuthViewModel
import com.example.bolava.data.AuthState
import com.example.bolava.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment @Inject constructor() : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewmodel by viewModels<AuthViewModel>()
    private lateinit var authActivity: AuthActivity

    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        authActivity = activity as AuthActivity

        setupButtons()
        setupRepositoryToViewModelObserver()

        lifecycleScope.launch {
            setupCollectors()
        }
    }

    private fun setupRepositoryToViewModelObserver() {
        viewmodel.authRepository.loginState.observe(viewLifecycleOwner, {
            viewmodel.loginState.value = it
            if (viewmodel.authRepository.loginState.value != AuthState.EMPTY) {
                viewmodel.authRepository.loginState.postValue(AuthState.EMPTY)
            }
        })
    }


    private suspend fun setupCollectors() {
        viewmodel.loginState.collect {
            when (it) {
                AuthState.SUCCESS -> {
                    binding.holderProgressBar.holderProgressBar.animate().apply {
                        duration = resources.getInteger(R.integer.shortAnim).toLong()
                        interpolator = FastOutSlowInInterpolator()
                        alpha(0f)
                        binding.holderProgressBar.holderProgressBar.visibility = View.GONE
                    }
                    authActivity.snackbarMessage("Logged in succesfully.")
                    authActivity.changeToUserActivity()
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
        binding.btnLogin.setOnClickListener {
            if (binding.inputEmail.text.isEmpty()) binding.inputEmail.error =
                resources.getString(R.string.error)
            if (binding.inputPassword.text.isEmpty()) binding.inputPassword.error =
                resources.getString(R.string.error)
            if (binding.inputEmail.text.isNotEmpty() &&
                binding.inputPassword.text.isNotEmpty()) {

                val currentUser = firebaseAuth.currentUser
                if (currentUser != null) {
                    authActivity.snackbarMessage("You are already logged in.")
                    authActivity.changeToUserActivity()
                } else viewmodel.loginUser(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())

            } else authActivity.snackbarMessage("Min. length for password is 6 characters.")
        }

        binding.textRegister.setOnClickListener {
            authActivity.changeFragment()
        }
    }
}