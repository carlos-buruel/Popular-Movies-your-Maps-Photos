package com.example.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import android.provider.MediaStore

class TestActivity: AppCompatActivity() {
	val PICK_IMAGE_MULTIPLE = 1
	var imageEncoded = ""
	var imagesEncodedList = ArrayList<String>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(TextView(this).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -1)
			gravity = Gravity.CENTER
			text = "Upload file"
			textSize = 30f

			setOnClickListener {
				Intent().apply {
					type = "image/*"
					putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
					action = Intent.ACTION_GET_CONTENT
					startActivityForResult(
						Intent.createChooser(this, "Elija sus photos"), 1
					)
				}
			}
		})

		val storage = Firebase.storage("gs://movies-maps-and-photos.appspot.com")
		val storageRef = storage.reference
		val imageRef = storageRef.child("images/test1.jpg")

		val stream = FileInputStream(
			File("/storage/emulated/0/DCIM/Facebook/FB_IMG_1642475299046.jpg"))

		val uploadTask = imageRef.putStream(stream)
		uploadTask.addOnFailureListener { error ->
			error.toString()
		}
		uploadTask.addOnSuccessListener { taskSnapshot ->
			taskSnapshot.toString()
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
			val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
			imagesEncodedList.clear()
			if (data.data != null) {

			} else {
				data.clipData?.let { mClipData ->
					for (index in 0 until mClipData.itemCount) {
						val item = mClipData.getItemAt(index)
						val uri = item.uri

						getImagePath(uri)
					}
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data)
	}

	private fun getImagePath(uri: Uri) {
		val file = File(uri.path)
		val filePath = file.path.split(":")
		val image_id = filePath[filePath.size - 1]

		val cursor = contentResolver.query(
			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			null,
			MediaStore.Images.Media._ID + " = ? ",
			arrayOf(image_id),
			null
		)
		if (cursor != null) {
			cursor.moveToFirst()
			cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA) ?: 0)
			cursor.close()
		}
	}
}