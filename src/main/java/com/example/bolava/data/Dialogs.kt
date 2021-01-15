package com.example.bolava.data

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.bolava.databinding.DialogLogoutBinding
import com.example.bolava.feature.user.UserActivity
import com.example.bolava.feature.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class Dialogs @Inject constructor(
    val context: Context
) {

    fun confirmLogOut() {
        val builder = AlertDialog.Builder(context)
        val binding = DialogLogoutBinding.inflate(LayoutInflater.from(context))
        builder.apply {
            setView(binding.root)
        }
        val dialog = builder.create()
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(context.resources.getColor(android.R.color.transparent)))

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            (context as UserActivity).viewmodel.currentUser.postValue(null)
            dialog.dismiss()
        }
    }
}