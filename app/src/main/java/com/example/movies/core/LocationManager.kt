package com.example.movies.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.movies.data.network.LocationService
import com.google.android.gms.location.*

class LocationManager(private val context: Context) {
	private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
	private lateinit var locationCallback: LocationCallback

	@SuppressLint("MissingPermission")
	fun onResume() {
		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

		locationCallback = object: LocationCallback() {
			override fun onLocationResult(locationResult: LocationResult) {
				for (location in locationResult.locations) {
					LocationService().setLocation(location.longitude, location.latitude)
				}
			}
		}

		val locationRequest = LocationRequest.create().apply {
			interval = 30000// 1800000
			fastestInterval = 25000// 900000
			priority = LocationRequest.PRIORITY_HIGH_ACCURACY
		}
		fusedLocationProviderClient.requestLocationUpdates(
			locationRequest,
			locationCallback,
			Looper.getMainLooper())
	}
}