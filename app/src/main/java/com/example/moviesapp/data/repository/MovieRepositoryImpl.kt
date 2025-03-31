package com.example.moviesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesapp.data.database.FavouriteMovieDao
import com.example.moviesapp.data.database.FavouriteMovieWithDetailsEntity
import com.example.moviesapp.data.mappers.toCountryDbEntity
import com.example.moviesapp.data.mappers.toDomain
import com.example.moviesapp.data.mappers.toDomainList
import com.example.moviesapp.data.mappers.toGenreDbEntity
import com.example.moviesapp.data.mappers.toMovieDbEntity
import com.example.moviesapp.data.network.KinopoiskApiService
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.presentation.sources.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val kinopoiskApiService: KinopoiskApiService,
    private val favouriteMovieDao: FavouriteMovieDao
): MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<MovieEntity> {
        val response = kinopoiskApiService.getPopularMovies(page = page)
        return response.toDomainList()
    }
    override suspend fun searchMovies(query: String): List<MovieEntity> {
        val response = kinopoiskApiService.searchMovies(query = query)
        return response.toDomainList()
    }
    override suspend fun addToFavorites(movie: MovieEntity) {
        val favoriteEntity = movie.toMovieDbEntity()
        val countries = movie.toCountryDbEntity()
        val genres = movie.toGenreDbEntity()
        favouriteMovieDao.insertFavoriteMovieWithDetails(favoriteEntity, countries, genres)
    }

    override suspend fun removeFromFavorites(movie: MovieEntity) {
        val favouriteEntity = movie.toMovieDbEntity()
        favouriteMovieDao.deleteFavouriteMovieWithDetails(favouriteEntity)
    }

    override fun getFavouriteMovies(): Flow<List<MovieEntity>> {
        return favouriteMovieDao.getAllFavouritesWithDetails().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun searchFavouriteMovies(
        movies: List<MovieEntity>,
        query: String
    ): List<MovieEntity> {
        if (query.isBlank()) return movies
        return movies.filter { movie ->
            movie.name.contains(query, ignoreCase = true) ||
                    movie.description.contains(query, ignoreCase = true) ||
                    movie.countries.any { it.country.contains(query, ignoreCase = true) } ||
                    movie.genres.any { it.genre.contains(query, ignoreCase = true) }
        }
    }

    override fun getPopularMoviesPagingData(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 60, maxSize = 200, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(repository = this, limit = 60) }
        ).flow
    }
}