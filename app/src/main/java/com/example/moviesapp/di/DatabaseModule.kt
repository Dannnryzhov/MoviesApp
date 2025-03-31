package com.example.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.data.database.AppDatabase
import com.example.moviesapp.data.database.FavouriteMovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "movies.db")
            .build()

    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase): FavouriteMovieDao =
        database.favouriteMovieDao()
}