package com.example.bolava.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bolava.data.User

class UserViewModel @ViewModelInject constructor(
) : ViewModel() {

    val currentUser = MutableLiveData<User>()
}