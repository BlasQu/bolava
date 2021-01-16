package com.example.bolava.feature.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bolava.data.AuthState
import com.example.bolava.data.User
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(
    val repository: UserRepository
) : ViewModel() {

    val changePasswordState = MutableStateFlow<AuthState>(AuthState.EMPTY)
    val currentUser = MutableLiveData<User>()

    fun changePassword(oldPassword: AuthCredential, newPassword: String) {
        viewModelScope.launch {
            changePasswordState.value = AuthState.LOADING
            repository.changePassword(oldPassword, newPassword)
        }
    }
}