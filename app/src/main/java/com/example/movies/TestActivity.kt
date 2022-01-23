package com.example.movies

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*

class TestActivity: AppCompatActivity() {
	private val pickImageMultiple = 100
	private val database = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(TextView(this).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -1)
			gravity = Gravity.CENTER
			val msg = "Upload file"
			text = msg
			textSize = 30f

			setOnClickListener {
				Intent().apply {
					type = "image/*"
					putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
					action = Intent.ACTION_GET_CONTENT
					startActivityForResult(
						Intent.createChooser(this, "Elija sus photos"), pickImageMultiple
					)
				}
			}
		})
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == pickImageMultiple && resultCode == RESULT_OK && data != null) {
			if (data.data != null) {
				data.data?.let { contentURI ->
					val bitmap = getBitmap(contentResolver, contentURI)
					uploadBitmap(bitmap)
				}
			} else {
				data.clipData?.let { mClipData ->
					for (index in 0 until mClipData.itemCount) {
						val item = mClipData.getItemAt(index)
						val uri = item.uri
						val bitmap = getBitmap(contentResolver, uri)
						uploadBitmap(bitmap)
					}
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data)
	}

	@Suppress("deprecation")
	private fun getBitmap(contentResolver: ContentResolver, uri: Uri): Bitmap {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			val source = ImageDecoder.createSource(contentResolver, uri)
			ImageDecoder.decodeBitmap(source)
		} else {
			MediaStore.Images.Media.getBitmap(contentResolver, uri)
		}
	}

	private fun uploadBitmap(bitmap: Bitmap) {
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