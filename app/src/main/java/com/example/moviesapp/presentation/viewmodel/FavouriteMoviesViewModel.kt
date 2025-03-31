package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.usecases.ManageFavouriteUseCase
import com.example.moviesapp.domain.usecases.SearchFavouriteMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    private val manageFavouriteUseCase: ManageFavouriteUseCase,
    private val searchFavouriteMoviesUseCase: SearchFavouriteMoviesUseCase
) : SearchViewModel() {

    val favouriteMovies: Flow<List<MovieEntity>> = manageFavouriteUseCase.getFavourites()

    override suspend fun performSearch(query: String): List<MovieEntity> {
        val movies = favouriteMovies.first()
        return searchFavouriteMoviesUseCase(movies, query)
    }

    fun removeFavouriteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            manageFavouriteUseCase.remove(movie)
        }
    }
}