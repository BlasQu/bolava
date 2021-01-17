package com.example.bolava.data

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.bolava.feature.user.UserActivity
import javax.inject.Inject

class Permissions @Inject constructor(
    var context: Context,
) {

    fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as UserActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Dialogs(context).permissionMessage()
            } else {
                (context as UserActivity).requestPermissions()
            }
        }
    }
}