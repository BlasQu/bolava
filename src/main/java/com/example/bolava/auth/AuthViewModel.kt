package com.example.bolava.auth

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bolava.data.AuthState
import com.example.bolava.data.AuthUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    val authRepository: AuthRepository
) : ViewModel() {

    val registrationState= MutableStateFlow<AuthState>(AuthState.EMPTY)

    var currentUser = MutableLiveData<AuthUser>()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            registrationState.value = AuthState.LOADING
            authRepository.registerUser(email, password)
        }
    }

}