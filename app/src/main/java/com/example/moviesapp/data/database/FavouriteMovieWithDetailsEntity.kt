package com.example.moviesapp.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class FavouriteMovieWithDetailsEntity(
    @Embedded val movie: MovieDbEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val countries: List<CountryDbEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val genres: List<GenreDbEntity>
)
