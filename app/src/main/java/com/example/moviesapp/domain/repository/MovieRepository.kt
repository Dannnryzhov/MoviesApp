package com.example.moviesapp.domain.repository

import androidx.paging.PagingData
import com.example.moviesapp.domain.models.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<MovieEntity>
    suspend fun searchMovies(query: String): List<MovieEntity>
    suspend fun addToFavorites(movie: MovieEntity)
    suspend fun removeFromFavorites(movie: MovieEntity)
    fun getFavouriteMovies(): Flow<List<MovieEntity>>
    suspend fun searchFavouriteMovies (movies: List<MovieEntity>,query: String): List<MovieEntity>
    fun getPopularMoviesPagingData(): Flow<PagingData<MovieEntity>>
}
