package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.models.MovieEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class SearchViewModel : BaseViewModel() {

    protected val mutableMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> = mutableMovies.asStateFlow()

    private val mutableSearchResults = MutableStateFlow<List<MovieEntity>>(emptyList())
    val searchResults: StateFlow<List<MovieEntity>> = mutableSearchResults.asStateFlow()

    protected abstract suspend fun performSearch(query: String): List<MovieEntity>

    fun search(query: String) {
        if (query.isBlank()) {
            mutableSearchResults.value = mutableMovies.value
            return
        }
        viewModelScope.launch(exceptionHandler) {
            val results = performSearch(query)
            mutableSearchResults.value = results
        }
    }
}
