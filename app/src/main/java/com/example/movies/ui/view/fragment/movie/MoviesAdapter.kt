package com.example.movies.ui.view.fragment.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.MovieModel

class MoviesAdapter(
    private val data: List<MovieModel>
): RecyclerView.Adapter<MovieHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = data[position]
        holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_movie, parent, false)
        return MovieHolder(view)
    }
}