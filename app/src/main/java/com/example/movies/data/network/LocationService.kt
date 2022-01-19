package com.example.movies.data.network

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationService {
	private val database = Firebase.firestore

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