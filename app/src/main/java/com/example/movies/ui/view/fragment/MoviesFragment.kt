package com.example.movies.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
            onCreate()
            moviesModel.observe(this@MoviesFragment, { moviesList ->
                val adapter = MoviesAdapter(moviesList)
                binding.run {
                    rvMovies.layoutManager = LinearLayoutManager(requireActivity())
                    rvMovies.adapter = adapter
                }
            })
            isLoading.observe(this@MoviesFragment, { isVisible ->
                binding.progress.isVisible = isVisible
            })
        }
    }
}