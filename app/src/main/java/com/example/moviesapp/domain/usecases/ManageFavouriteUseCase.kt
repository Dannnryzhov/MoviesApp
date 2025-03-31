package com.example.moviesapp.domain.usecases

import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class ManageFavouriteUseCase @Inject constructor (
    private val repository: MovieRepository
) {
    suspend fun add(movie: MovieEntity) = repository.addToFavorites(movie)
    suspend fun remove(movie: MovieEntity) = repository.removeFromFavorites(movie)
    fun getFavourites() = repository.getFavouriteMovies()
}