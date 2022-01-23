package com.example.movies.core

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration: RecyclerView.ItemDecoration() {
	private val space = 30
	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		outRect.top = if (parent.getChildLayoutPosition(view) in 0..1) 0 else space
	}
}