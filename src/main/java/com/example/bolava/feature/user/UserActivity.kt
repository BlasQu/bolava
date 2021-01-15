package com.example.bolava.feature.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import com.example.bolava.R
import com.example.bolava.data.Dialogs
import com.example.bolava.data.User
import com.example.bolava.databinding.ActivityUserBinding
import com.example.bolava.databinding.NavigationHeaderBinding
import com.example.bolava.feature.auth.AuthActivity
import com.example.bolava.feature.user.fragments.HistoryFragment
import com.example.bolava.feature.user.fragments.MapFragment
import com.example.bolava.feature.user.fragments.SettingsFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity @Inject constructor(
) : AppCompatActivity() {

    val viewmodel by viewModels<UserViewModel>()
    lateinit var binding: ActivityUserBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var header: NavigationHeaderBinding

    @Inject
    lateinit var mapFragment: MapFragment

    @Inject
    lateinit var settingsFragment: SettingsFragment

    @Inject
    lateinit var historyFragment: HistoryFragment

    @Inject
    lateinit var dialogs: Dialogs

    override fun onBackPressed() {
        val index = supportFragmentManager.backStackEntryCount - 1
        val currentFragment = supportFragmentManager.getBackStackEntryAt(index).name!!

        when (currentFragment) {
            "SettingsFragment" -> {
                setupFragment()
            }
            "HistoryFragment" -> {
                setupFragment()
            }
            "MapFragment" -> {
                dialogs.confirmLogOut()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()

        header = NavigationHeaderBinding.inflate(layoutInflater)
        binding.navigationView.addHeaderView(header.root)

        viewmodel.currentUser.observe(this, {
            if (it == null) signOut()
            else setupDrawer()
        })

        viewmodel.currentUser.postValue(User(firebaseAuth.currentUser!!.uid, firebaseAuth.currentUser!!.email!!))
    }

    private fun signOut() {
        firebaseAuth.signOut()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, mapFragment)
            addToBackStack("MapFragment")
            commit()
        }
    }

    private fun changeFragment(changeTo: String) {
        when (changeTo) {
            "SettingsFragment" -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, settingsFragment)
                    addToBackStack("SettingsFragment")
                    commit()
                }
            }
            "HistoryFragment" -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, historyFragment)
                    addToBackStack("HistoryFragment")
                    commit()
                }
            }
        }
    }

    private fun setupDrawer() {
        val currentUser: User? = viewmodel.currentUser.value
        val currentUsername = currentUser!!.email
        header.username.text = currentUsername

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    changeFragment("SettingsFragment")
                    return@setNavigationItemSelectedListener true
                }
                R.id.history -> {
                    changeFragment("HistoryFragment")
                    return@setNavigationItemSelectedListener true
                }
                R.id.logout -> {
                    dialogs.confirmLogOut()
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener true
            }
        }
    }

    fun snackbarMessage(message: String = "Something went wrong...") {
        Snackbar.make(binding.layout, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun changeFragment() {

    }
}