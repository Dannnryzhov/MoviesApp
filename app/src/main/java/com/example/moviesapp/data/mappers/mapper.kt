package com.example.moviesapp.data.mappers

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
        poster = this.poster.toDomain(),
        countries = this.countries.map { it.toDomain() },
        genres = this.genres.map { it.toDomain() }
    )
}

fun MovieResponse.toDomainList(): List<MovieEntity> {
    return this.movies.map { it.toDomain() }
}