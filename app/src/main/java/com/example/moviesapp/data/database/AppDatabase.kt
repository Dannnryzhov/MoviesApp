package com.example.moviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieDbEntity::class, CountryDbEntity::class, GenreDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteMovieDao(): FavouriteMovieDao
}