package com.example.movies.data.network

import android.graphics.Bitmap
import android.util.Log
import com.example.movies.ui.view.fragment.images.ImageContract
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class ImageService {
	private val database = Firebase.firestore

	fun getImages(view: ImageContract) {
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

	fun uploadBitmap(bitmap: Bitmap) {
		val output = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
		val data1 = output.toByteArray()

		val storage = Firebase.storage("gs://movies-maps-and-photos.appspot.com")
		val storageRef = storage.reference

		val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

		val uploadTask = imageRef.putBytes(data1)
		uploadTask.addOnFailureListener { error ->
			error.toString()
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
			.addOnSuccessListener { documentReference ->
				Log.d("IMAGE_ID", documentReference.id)
			}
			.addOnFailureListener { error ->
				Log.d("IMAGE_ID", "Error registered document", error)
			}
	}
}