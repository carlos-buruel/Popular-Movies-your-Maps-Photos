package com.example.movies.data.network

import android.graphics.Bitmap
import android.util.Log
import com.example.movies.ui.view.fragment.images.ImageContract
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*

class ImageService {
	private val database = Firebase.firestore

	fun getImages(view: ImageContract) {
		database.collection("images")
			.addSnapshotListener { snapshots, error ->
				error?.let {
					Log.e("IMAGE_ID", "Error Escucha fallo", error)
				} ?: run {
					snapshots?.documentChanges?.let { documents ->
						if (documents.size == 0) {
							view.setUrlImages("")
							return@addSnapshotListener
						}
						for (documentChanged in documents) {
							when (documentChanged.type) {
								DocumentChange.Type.ADDED -> {
									val data = documentChanged.document.data
									val url = data["url_image"].toString()
									if (url.isNotEmpty()) {
										view.setUrlImages(url)
									}
								}
								else -> {}
							}
						}
					}
				}
			}
	}

	fun uploadBitmap(bitmap: Bitmap) {
		val output = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
		val data1 = output.toByteArray()

		val storage = Firebase.storage("gs://movies-maps-and-photos.appspot.com")
		val storageRef = storage.reference

		val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

		val uploadTask = imageRef.putBytes(data1)
		uploadTask.addOnFailureListener { error ->
			Log.e("IMAGE_ID", "Error on upload image", error)
		}
		uploadTask.addOnSuccessListener { taskSnapshot ->
			taskSnapshot.toString()
			registerUrl(imageRef)
		}
	}

	private fun registerUrl(reference: StorageReference) {
		reference.downloadUrl.addOnSuccessListener {
			registerUrl("$it")
		}.addOnFailureListener {
			it.toString()
		}
	}

	private fun registerUrl(url: String) {
		val imageData = hashMapOf(
			"url_image" to url
		)
		database.collection("images")
			.add(imageData)
			.addOnFailureListener { error ->
				Log.e("IMAGE_ID", "Error registered image", error)
			}
	}
}