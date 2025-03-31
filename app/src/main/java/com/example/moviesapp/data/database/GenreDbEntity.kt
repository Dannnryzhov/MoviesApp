package com.example.moviesapp.data.database

import androidx.room.Entity

@Entity(tableName = "movie_genres", primaryKeys = ["movieId", "genre"])
data class GenreDbEntity(
    val movieId: Int,
    val genre: String
)