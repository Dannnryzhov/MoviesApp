package com.example.moviesapp.data.mappers

import com.example.moviesapp.data.database.CountryDbEntity
import com.example.moviesapp.data.database.FavouriteMovieWithDetailsEntity
import com.example.moviesapp.data.database.GenreDbEntity
import com.example.moviesapp.data.database.MovieDbEntity
import com.example.moviesapp.data.network.model.CountryDto
import com.example.moviesapp.data.network.model.GenreDto
import com.example.moviesapp.data.network.model.MovieDto
import com.example.moviesapp.data.network.model.MovieResponse
import com.example.moviesapp.data.network.model.PosterDto
import com.example.moviesapp.domain.models.CountryEntity
import com.example.moviesapp.domain.models.GenreEntity
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.models.PosterEntity

fun CountryDto.toDomain(): CountryEntity {
    return CountryEntity(
        country = this.country
    )
}

fun GenreDto.toDomain(): GenreEntity {
    return GenreEntity(
        genre = this.genre
    )
}

fun PosterDto.toDomain(): PosterEntity {
    return PosterEntity(
        url = this.url
    )
}

fun MovieDto.toDomain(): MovieEntity {
    return MovieEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        year = this.year,
        poster = PosterEntity(url = this.poster.url ?: "default_url"),
        countries = this.countries.map { it.toDomain() },
        genres = this.genres.map { it.toDomain() }
    )
}

fun MovieResponse.toDomainList(): List<MovieEntity> {
    return this.movies.map { it.toDomain() }
}
fun MovieEntity.toMovieDbEntity(): MovieDbEntity =
    MovieDbEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        year = this.year,
        posterUrl = this.poster.url
    )

fun MovieEntity.toCountryDbEntity(): List<CountryDbEntity> =
    this.countries.map {
        CountryDbEntity(movieId = this.id, country = it.country)
    }

fun MovieEntity.toGenreDbEntity(): List<GenreDbEntity> =
    this.genres.map {
        GenreDbEntity(movieId = this.id, genre = it.genre)
    }

fun FavouriteMovieWithDetailsEntity.toDomain(): MovieEntity =
    MovieEntity(
        id = movie.id,
        name = movie.name,
        description = movie.description,
        year = movie.year,
        poster = PosterEntity(url = movie.posterUrl),
        countries = countries.map { CountryEntity(it.country) },
        genres = genres.map { GenreEntity(it.genre) }
    )
