package com.example.movies.ui.view.fragment.images

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.core.SpacesItemDecoration
import com.example.movies.data.network.ImageService
import com.example.movies.databinding.FragmentImageGaleryBinding

class ImageFragment: Fragment(), ImageContract {
	private lateinit var binding: FragmentImageGaleryBinding
	private val imageService = ImageService()
	private lateinit var imagesAdapter: ImagesAdapter
	private val aUrlImages = ArrayList<String>()

	private val getResult = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { activityResult ->
		val data = activityResult.data
		data?.data?.let { contentURI ->
			val bitmap = getBitmap(requireActivity().contentResolver, contentURI)
			imageService.uploadBitmap(bitmap)
		} ?: run {
			data?.clipData?.let { clipData ->
				for (index in 0 until clipData.itemCount) {
					val item = clipData.getItemAt(index)
					val uri = item.uri
					val bitmap = getBitmap(requireActivity().contentResolver, uri)
					imageService.uploadBitmap(bitmap)
				}
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentImageGaleryBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		ImageService().getImages(this)
		binding.run {
			tvUpload.stateListAnimator = null
			tvUpload.setOnClickListener {
				val intentImage = Intent().apply {
					type = "image/*"
					putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
					action = Intent.ACTION_GET_CONTENT
				}
				val intent = Intent.createChooser(intentImage, "Elija sus photos")
				getResult.launch(intent)
			}
			//region initList
			imagesAdapter = ImagesAdapter(aUrlImages)
			binding.run {
				rvImages.run {
					layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
					addItemDecoration(SpacesItemDecoration())
					adapter = imagesAdapter
				}
			}
			//endregion
		}
	}

	override fun setUrlImages(urlImages: String) {
		binding.run {
			if (progress.isVisible) {
				progress.isVisible = false
			}

			if (urlImages.isNotEmpty()) {
				tvMessage.visibility = View.GONE

				aUrlImages.add(urlImages)
				imagesAdapter.notifyItemRangeChanged(aUrlImages.size - 1, 1)
			} else {
				tvMessage.visibility = View.VISIBLE
			}
		}
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
}