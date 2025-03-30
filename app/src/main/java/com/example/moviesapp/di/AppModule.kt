package com.example.moviesapp.di

import com.example.moviesapp.data.repository.MovieRepositoryImpl
import com.example.moviesapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}