package com.example.moviesapp.domain.usecases

import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): List<MovieEntity> {
        return repository.getPopularMovies(page)
    }
}