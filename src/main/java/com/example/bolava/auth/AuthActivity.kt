package com.example.bolava.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.bolava.R
import com.example.bolava.databinding.ActivityAuthBinding
import com.example.bolava.fragments.LoginFragment
import com.example.bolava.fragments.RegisterFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class AuthActivity @Inject constructor() : AppCompatActivity() {

    @Inject
    lateinit var loginFragment: LoginFragment

    @Inject
    lateinit var registerFragment: RegisterFragment

    private lateinit var binding: ActivityAuthBinding
    private val viewmodel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, loginFragment)
            addToBackStack("LoginFragment")
            commit()
        }
    }

    fun snackbarMessage(message: String = "Something went wrong...") {
        Snackbar.make(binding.layout, message, Snackbar.LENGTH_SHORT).show()
    }

    fun changeFragment() {
        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {
            "LoginFragment" -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, registerFragment)
                    addToBackStack("RegisterFragment")
                    commit()
                }
            }

            "RegisterFragment" -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, loginFragment)
                    addToBackStack("LoginFragment")
                    commit()
                }
            }

            else -> snackbarMessage()
        }
    }
}