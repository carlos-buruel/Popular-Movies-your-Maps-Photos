package com.example.movies.data.network

import com.example.movies.ui.view.fragment.images.ImageContract
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ImageService {
	private val database = Firebase.firestore

	fun getLocation(view: ImageContract) {
		database.collection("images")
			.get()
			.addOnSuccessListener { result ->
				val aUrlList = ArrayList<String>()
				for (document in result) {
					val url = document.getString("url_image") ?: ""
					aUrlList.add(url)
				}
				view.getUrlImages(aUrlList)
			}
			.addOnFailureListener { exception ->
				exception.toString()
			}
	}
}