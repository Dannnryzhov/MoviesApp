package com.example.moviesapp.domain.models

data class MovieEntity (
    val id: Int,
    val name: String,
    val description: String,
    val year: Int,
    val poster: PosterEntity,
    val countries: List<CountryEntity>,
    val genres: List<GenreEntity>
)