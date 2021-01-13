package com.example.bolava.auth

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bolava.data.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    val authRepository: AuthRepository
) : ViewModel() {

    val registrationState = MutableStateFlow<AuthState>(AuthState.EMPTY)
    val loginState = MutableStateFlow<AuthState>(AuthState.EMPTY)

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            registrationState.value = AuthState.LOADING
            authRepository.registerUser(email, password)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = AuthState.LOADING
            authRepository.loginUser(email, password)
        }
    }
}