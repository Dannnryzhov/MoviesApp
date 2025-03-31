package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.models.MovieEntity

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<MovieEntity>
    suspend fun searchMovies(query: String): List<MovieEntity>
}