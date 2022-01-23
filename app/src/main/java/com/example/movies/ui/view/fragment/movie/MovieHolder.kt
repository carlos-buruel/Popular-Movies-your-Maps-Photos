package com.example.movies.ui.view.fragment.movie

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.core.Constant.urlImage
import com.example.movies.core.DrawableBuilder
import com.example.movies.ui.components.RoundCorners
import com.example.movies.data.model.MovieModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

class MovieHolder(view: View): RecyclerView.ViewHolder(view) {
    private val rlParent = view.findViewById<View>(R.id.rlParent) ?: null
    private val ivMovie = view.findViewById<ImageView>(R.id.ivMovie) ?: null
    private val tvTitle = view.findViewById<TextView>(R.id.tvTitle) ?: null
    private val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate) ?: null
    private val tvVoteAverage = view.findViewById<TextView>(R.id.tvVoteAverageCount) ?: null

    init {
        rlParent?.run {
            background = DrawableBuilder.setGradientDrawable(
                ContextCompat.getColor(context, R.color.secondary_color), 30f
            )
        }
    }

    fun bind(movie: MovieModel) {
        movie.run {
            loadImage(posterPath)
            val contentTitle = title
            tvTitle?.text = contentTitle
            val contentReleaseDate = "Fecha de estreno ${formatDate(releaseDate)}"
            tvReleaseDate?.text = contentReleaseDate
            val contentAverageCount = "${voteAverage.percent()}% de $voteCount les gusto"
            tvVoteAverage?.text = contentAverageCount
        }
    }

    private fun loadImage(imagePath: String) {
        Picasso.get()
            .load(urlImage + imagePath)
            .transform(RoundCorners())
            .placeholder(R.drawable.picture)
            .into(ivMovie)
    }

    private fun formatDate(dateString: String): String {
        val dateFormatIn = SimpleDateFormat("yyyy-MM-dd", Locale("es", "MX"))
        val date = dateFormatIn.parse(dateString) ?: Date()
        val dateFormatOut = SimpleDateFormat("dd MMM yyyy", Locale("es", "MX"))
        return dateFormatOut.format(date) ?: ""
    }

    private fun Double.percent(): String {
        val percent = this * 10
        return "${floor(percent).toInt()}"
    }
}