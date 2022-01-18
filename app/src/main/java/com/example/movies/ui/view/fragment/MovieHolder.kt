package com.example.movies.ui.view.fragment

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.MovieModel

class MovieHolder(view: View): RecyclerView.ViewHolder(view) {
    private val tvTitle = view.findViewById<TextView>(R.id.tvTitle) ?: null
    private val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate) ?: null
    private val tvVoteAverage = view.findViewById<TextView>(R.id.tvVoteAverage) ?: null
    private val tvVoteCount = view.findViewById<TextView>(R.id.tvVoteCount) ?: null

    fun bind(movie: MovieModel) {
        movie.run {
            val contentTitle = "Titulo $title"
            tvTitle?.text = contentTitle
            val contentReleaseDate = "Fecha de estreno $releaseDate"
            tvReleaseDate?.text = contentReleaseDate
            val contentAverage = "Calificación de $voteAverage"
            tvVoteAverage?.text = contentAverage
            val contentVote = "$voteCount de votos"
            tvVoteCount?.text = contentVote
        }
    }
}