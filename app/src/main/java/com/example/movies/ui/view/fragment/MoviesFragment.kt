package com.example.movies.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.data.db.DatabaseHelper
import com.example.movies.data.model.MovieModel
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.ui.viewModel.MovieViewModel

class MoviesFragment: Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieViewModel.run {
            val databaseHelper = DatabaseHelper(requireContext())

            onCreate()
            moviesModel.observe(this@MoviesFragment, { moviesList ->
                val recoveredMovies = databaseHelper.recover()

                //region if movie from services is not empty, save registers
                if (moviesList.isNotEmpty() && recoveredMovies.isEmpty()) {
                    moviesList.forEach { databaseHelper.insert(it) }
                }
                else if (moviesList.size != recoveredMovies.size) {
                    databaseHelper.clearTable()
                    moviesList.forEach { databaseHelper.insert(it) }
                }
                //endregion
                val currentList = ArrayList<MovieModel>()
                //region fill data from database
                if (moviesList.isEmpty() && recoveredMovies.isNotEmpty()) {
                    currentList.addAll(recoveredMovies)
                }
                else if (moviesList.isNotEmpty()) {
                    currentList.addAll(moviesList)
                }

                binding.run {
                    if (currentList.isEmpty()) {
                        binding.tvMessage.visibility = View.VISIBLE
                    }
                    else {
                        val adapter = MoviesAdapter(currentList)
                        rvMovies.layoutManager = LinearLayoutManager(requireActivity())
                        rvMovies.adapter = adapter
                    }
                }
            })
            isLoading.observe(this@MoviesFragment, { isVisible ->
                binding.progress.isVisible = isVisible
            })
        }
    }
}