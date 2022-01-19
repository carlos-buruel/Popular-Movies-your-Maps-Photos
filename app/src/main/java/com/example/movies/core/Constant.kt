package com.example.movies.core

object Constant {
    const val urlImage = "https://www.themoviedb.org/t/p/w220_and_h330_face/"
    const val apiKey = "91baf64be9bfb1e34d302382383e41aa"
    const val language = "en-US"
    const val page = 1

    val nameTabs = arrayListOf("Movies", "Location")
    //region Database
    const val databaseName = "dbMovies"
    const val moviesTableName = "tMovies"
    const val databaseVersion = 1
    //endregion
}