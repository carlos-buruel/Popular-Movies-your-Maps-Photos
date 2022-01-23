package com.example.movies.data.network

import android.util.Log
import com.example.movies.data.model.LatitudeLongitude
import com.example.movies.ui.view.fragment.map.MapHistoryContract
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LocationService {
	private val database = Firebase.firestore

	fun getLocation(view: MapHistoryContract) {
		database.collection("locations")
			.get()
			.addOnSuccessListener { result ->
				val list = ArrayList<LatitudeLongitude>()
				for (document in result) {
					val obj = document.toObject<LatitudeLongitude>()
					list.add(obj)
				}
				val latitudeMax = list.maxByOrNull { it.latitude ?: 0.0 }?.latitude ?: 0.0
				val latitudeMin = list.minByOrNull { it.latitude ?: 0.0 }?.latitude ?: 0.0
				val longitudeMax = list.maxByOrNull { it.latitude ?: 0.0 }?.longitude ?: 0.0
				val longitudeMin = list.minByOrNull { it.latitude ?: 0.0 }?.longitude ?: 0.0

				view.getLocation(
					list,
					(latitudeMax + latitudeMin) / 2,
					(longitudeMax + longitudeMin) / 2
				)
			}
			.addOnFailureListener { exception ->
				exception.toString()
			}
	}

	fun setLocation(longitude: Double, latitude: Double) {
		val location = hashMapOf(
			"longitude" to longitude,
			"latitude" to latitude
		)

		database.collection("locations")
			.add(location)
			.addOnSuccessListener { documentReference ->
				Log.d("DOCUMENT_ID", documentReference.id)
			}
			.addOnFailureListener { error ->
				Log.d("DOCUMENT_ID", "Error registered document", error)
			}
	}
}