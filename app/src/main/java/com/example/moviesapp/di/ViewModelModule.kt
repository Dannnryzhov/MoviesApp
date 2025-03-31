package com.example.moviesapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.presentation.viewmodel.FavouriteMoviesViewModel
import com.example.moviesapp.presentation.viewmodel.HostViewModel
import com.example.moviesapp.presentation.viewmodel.MovieListViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    fun bindMovieListViewModel(movieListViewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteMoviesViewModel::class)
    fun bindFavouriteMoviesViewModel(favouriteMoviesViewModel: FavouriteMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HostViewModel::class)
    fun bindHostViewModel(hostViewModel: HostViewModel): ViewModel

    @Binds
    @Singleton
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}