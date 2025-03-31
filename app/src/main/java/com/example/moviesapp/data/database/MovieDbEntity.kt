package com.example.moviesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourite_movies")
data class MovieDbEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val year: Int,
    val posterUrl: String?,
)