package com.example.moviesapp.domain.usecases

import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class SearchFavouriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movies: List<MovieEntity>, query: String): List<MovieEntity> {
        return repository.searchFavouriteMovies(movies, query)
    }
}
