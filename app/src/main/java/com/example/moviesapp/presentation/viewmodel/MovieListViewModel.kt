package com.example.moviesapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.usecases.GetPopularMoviesUseCase
import com.example.moviesapp.domain.usecases.SearchMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : SearchViewModel() {

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch(exceptionHandler) {
            val moviesList = getMoviesUseCase(1)
            Log.d("MovieListVM", "Получено фильмов: ${moviesList.size}")
            mutableMovies.value = moviesList
        }
    }

    override suspend fun performSearch(query: String): List<MovieEntity> {
        return searchMoviesUseCase(query)
    }


    fun triggerTestError() {
        viewModelScope.launch(exceptionHandler) {
            throw RuntimeException("Тестовая ошибка для проверки диалога")
        }
    }
}
