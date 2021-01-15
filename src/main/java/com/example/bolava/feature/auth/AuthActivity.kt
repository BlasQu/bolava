package com.example.bolava.feature.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.bolava.R
import com.example.bolava.data.User
import com.example.bolava.databinding.ActivityAuthBinding
import com.example.bolava.feature.auth.fragments.LoginFragment
import com.example.bolava.feature.auth.fragments.RegisterFragment
import com.example.bolava.feature.user.UserActivity
import com.example.bolava.feature.user.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity @Inject constructor() : AppCompatActivity() {

    @Inject
    lateinit var loginFragment: LoginFragment

    @Inject
    lateinit var registerFragment: RegisterFragment

    @Inject
    lateinit var userActivity: UserActivity

    private lateinit var binding: ActivityAuthBinding
    private var firebaseAuth = FirebaseAuth.getInstance()

    private val viewmodel by viewModels<UserViewModel>() //different viewmodel than 'AUTH' to parse data between them

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

    fun changeToUserActivity() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            viewmodel.currentUser.postValue(User(currentUser.uid, currentUser.email!!))
            val userIntent = Intent(this, userActivity::class.java)
            startActivity(userIntent)
            finish()
        } else {
            snackbarMessage()
        }
    }

    override fun onBackPressed() {
        //Leave empty
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