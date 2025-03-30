package com.example.moviesapp.data.repository

import com.example.moviesapp.data.mappers.toDomainList
import com.example.moviesapp.data.network.KinopoiskApiService
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val kinopoiskApiService: KinopoiskApiService,
): MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<MovieEntity> {
        val response = kinopoiskApiService.getPopularMovies(page = page)
        return response.toDomainList()
    }
}