package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.usecases.GetPopularMoviesPagingDataUseCase
import com.example.moviesapp.domain.usecases.ManageFavouriteUseCase
import com.example.moviesapp.domain.usecases.SearchMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getPopularMoviesPagingDataUseCase: GetPopularMoviesPagingDataUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val manageFavouriteUseCase: ManageFavouriteUseCase
) : SearchViewModel() {

    val popularMovies: Flow<PagingData<MovieEntity>> =
        getPopularMoviesPagingDataUseCase().cachedIn(viewModelScope)

    val favoriteMovies: Flow<List<MovieEntity>> = manageFavouriteUseCase.getFavourites()

    fun addToFavouriteMovie(movie: MovieEntity) {
        viewModelScope.launch(exceptionHandler) {
            manageFavouriteUseCase.add(movie)
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

