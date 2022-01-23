package com.example.movies.ui.view.fragment.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R

class ImagesAdapter(
	private val aUrlImages: List<String>
): RecyclerView.Adapter<ImageHolder>() {
	override fun getItemCount(): Int = aUrlImages.size

	override fun onBindViewHolder(holder: ImageHolder, position: Int) {
		val url = aUrlImages[position]
		holder.onBind(url)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.holder_image, parent, false)
		return ImageHolder(view)
	}
}