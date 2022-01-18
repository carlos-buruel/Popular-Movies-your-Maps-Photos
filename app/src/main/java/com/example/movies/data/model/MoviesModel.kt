package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MoviesModel(
    @SerializedName("results")
    val results: List<MovieModel>
)