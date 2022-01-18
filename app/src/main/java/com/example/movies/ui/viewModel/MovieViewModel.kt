package com.example.movies.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieModel
import com.example.movies.domain.GetMoviesUseCase
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    val moviesModel = MutableLiveData<List<MovieModel>>()
    val isLoading = MutableLiveData<Boolean>()

    var getMovieUseCase = GetMoviesUseCase()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMovieUseCase()

            if (!result.results.isNullOrEmpty()) {
                moviesModel.postValue(result.results)
                isLoading.postValue(false)
            }
        }
    }
}