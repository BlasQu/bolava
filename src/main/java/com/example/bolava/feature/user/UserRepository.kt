package com.example.bolava.feature.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bolava.data.AuthState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepository @Inject constructor(
) {

    val state = MutableLiveData<AuthState>()
    val user = FirebaseAuth.getInstance().currentUser

    fun changePassword(oldPassword: AuthCredential, newPassword: String) {
        user!!.reauthenticate(oldPassword).addOnCompleteListener {
            if (it.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { update ->
                    if (update.isSuccessful) {
                        state.postValue(AuthState.SUCCESS)
                    } else {
                        state.postValue(AuthState.ERROR(update.exception?.message.toString()))
                        Log.d("FIREBASE_REGISTER", update.exception.toString())
                    }
                }
            } else {
                state.postValue(AuthState.ERROR(it.exception?.message.toString()))
                Log.d("FIREBASE_REGISTER", it.exception.toString())
            }
        }
    }
}