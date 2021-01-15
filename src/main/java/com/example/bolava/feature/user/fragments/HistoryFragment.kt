package com.example.bolava.feature.user.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bolava.R
import com.example.bolava.databinding.FragmentHistoryBinding
import com.example.bolava.databinding.FragmentMapBinding
import com.example.bolava.feature.user.UserActivity
import com.example.bolava.feature.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment @Inject constructor(
) : Fragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private val viewmodel by viewModels<UserViewModel>()
    private lateinit var userActivity: UserActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        userActivity = activity as UserActivity
    }



}