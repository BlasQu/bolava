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
import com.example.bolava.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewmodel by viewModels<SplashViewModel>()

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
            val changeActivity = Intent(this, AuthActivity::class.java)
            startActivity(changeActivity)
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        finish()
    }
}