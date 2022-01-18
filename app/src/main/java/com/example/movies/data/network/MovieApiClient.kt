package com.example.movies.data.network

import com.example.movies.data.model.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiClient {
    @GET("popular")
    suspend fun getAllMovies(
        @Query("api_key") apiQuery: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesModel>
}