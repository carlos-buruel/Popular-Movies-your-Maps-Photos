package com.example.movies.data

import com.example.movies.data.model.MoviesModel
import com.example.movies.data.model.MoviesProvider
import com.example.movies.data.network.MovieService

class MoviesRepository {
    private val api = MovieService()

    suspend fun getAllQuotes(): MoviesModel {
        val response = api.getAllMovies()
        MoviesProvider.movies = response
        return response
    }
}