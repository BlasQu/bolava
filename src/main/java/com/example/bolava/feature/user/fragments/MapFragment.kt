package com.example.bolava.feature.user.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bolava.R
import com.example.bolava.databinding.FragmentMapBinding
import com.example.bolava.feature.user.UserActivity
import com.example.bolava.feature.user.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment @Inject constructor(
) : Fragment(R.layout.fragment_map) {

    private lateinit var binding: FragmentMapBinding
    private val viewmodel by viewModels<UserViewModel>()
    private lateinit var userActivity: UserActivity

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        val locationManager = userActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val internet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f) {
            val currentLocation = LatLng(it.latitude, it.longitude)
            googleMap.addMarker(MarkerOptions().position(currentLocation))
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMapBinding.bind(view)
        userActivity = activity as UserActivity

        createMap()
    }

    fun createMap() {
        if (ActivityCompat.checkSelfPermission(userActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val map = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
            map?.getMapAsync(callback)
        }
    }

}