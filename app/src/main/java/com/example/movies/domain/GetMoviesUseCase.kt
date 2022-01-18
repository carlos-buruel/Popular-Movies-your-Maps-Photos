package com.example.movies.domain

import com.example.movies.data.MoviesRepository
import com.example.movies.data.model.MoviesModel

class GetMoviesUseCase {
    private val repository = MoviesRepository()

    suspend operator fun invoke(): MoviesModel = repository.getAllQuotes()
}