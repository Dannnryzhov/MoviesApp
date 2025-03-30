package com.example.moviesapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val mutableMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> = mutableMovies.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val moviesList = getMoviesUseCase(1)
                Log.d("MovieListVM", "Получено фильмов: ${moviesList.size}")
                mutableMovies.value = moviesList
            } catch (e: Exception) {
                Log.e("MovieListVM", "Ошибка при получении фильмов", e)
            }
        }
    }
}
