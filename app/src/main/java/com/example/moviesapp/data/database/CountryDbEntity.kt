package com.example.moviesapp.data.database

import androidx.room.Entity

@Entity(tableName = "movie_countries", primaryKeys = ["movieId", "country"])
data class CountryDbEntity(
    val movieId: Int,
    val country: String
)