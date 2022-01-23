package com.example.movies.ui.view.fragment.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.data.network.ImageService
import com.example.movies.databinding.FragmentImageGaleryBinding

class ImageFragment: Fragment(), ImageContract {
	private lateinit var binding: FragmentImageGaleryBinding
	private lateinit var imagesAdapter: ImagesAdapter
	private val aUrlImages = ArrayList<String>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentImageGaleryBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		ImageService().getLocation(this)
		binding.run {
			tvUpload.setOnClickListener {

			}
		}
	}

	override fun getUrlImages(aUrlImages: ArrayList<String>) {
		this.aUrlImages.clear()
		this.aUrlImages.addAll(aUrlImages)

		imagesAdapter = ImagesAdapter(aUrlImages)
		binding.run {
			rvImages.layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
			rvImages.adapter = imagesAdapter
		}
	}
}