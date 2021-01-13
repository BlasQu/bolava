package com.example.bolava.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.bolava.auth.AuthActivity
import com.example.bolava.R
import com.example.bolava.data.User
import com.example.bolava.databinding.ActivitySplashBinding
import com.example.bolava.user.UserActivity
import com.example.bolava.user.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewmodel by viewModels<UserViewModel>()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    @Inject
    lateinit var authActivity: AuthActivity

    @Inject
    lateinit var userActivity: UserActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateSplashActivity()
    }

    private fun animateSplashActivity() {
        binding.layout.animate().apply {
            interpolator = FastOutSlowInInterpolator()
            duration = resources.getInteger(R.integer.shortAnim).toLong()
            alpha(1f)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser == null) {
                val changeActivity = Intent(this, authActivity::class.java)
                startActivity(changeActivity)
                finish()
            } else {
                viewmodel.currentUser.postValue(User(currentUser.uid, currentUser.email!!))
                val changeActivity = Intent(this, userActivity::class.java)
                startActivity(changeActivity)
                finish()
            }
        }, 3000)
    }

    override fun onBackPressed() {
        finish()
    }
}