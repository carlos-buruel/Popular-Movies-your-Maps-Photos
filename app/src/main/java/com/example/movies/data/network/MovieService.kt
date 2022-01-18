package com.example.movies.data.network

import com.example.movies.core.Constant
import com.example.movies.core.RetrofitHelper
import com.example.movies.data.model.MoviesModel
import com.example.movies.data.model.emptyMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllMovies(): MoviesModel {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(MovieApiClient::class.java)
                .getAllMovies(
                    Constant.apiKey,
                    Constant.language,
                    Constant.page
                )
            response.body() ?: emptyMovies()
        }
    }
}