package com.example.bolava.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bolava.data.AuthState
import com.example.bolava.data.AuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    val state = MutableLiveData<AuthState>(AuthState.EMPTY)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) state.postValue(AuthState.SUCCESS)
            else {
                state.postValue(AuthState.ERROR(it.exception?.message.toString()))
                Log.d("FIREBASE", it.exception.toString())
            }
        }
    }
}