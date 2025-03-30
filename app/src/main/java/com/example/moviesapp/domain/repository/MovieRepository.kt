package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.models.MovieEntity

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<MovieEntity>
}