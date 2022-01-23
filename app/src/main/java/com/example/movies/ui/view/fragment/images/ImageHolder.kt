package com.example.movies.ui.view.fragment.images

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.ui.components.RoundCorners
import com.squareup.picasso.Picasso

class ImageHolder(view: View): RecyclerView.ViewHolder(view) {
	private val ivImage = view.findViewById<ImageView>(R.id.ivImage) ?: null

	fun onBind(url: String) {
		ivImage?.let {
			Picasso.get()
				.load(url)
				.transform(RoundCorners())
				.placeholder(R.drawable.picture)
				.into(it)
		}
	}
}