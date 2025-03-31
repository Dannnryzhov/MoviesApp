package com.example.moviesapp.di

import android.app.Application
import com.example.moviesapp.presentation.ui.activity.MainActivity
import com.example.moviesapp.presentation.ui.fragments.FavouriteMoviesFragment
import com.example.moviesapp.presentation.ui.fragments.HostFragment
import com.example.moviesapp.presentation.ui.fragments.MovieListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: MovieListFragment)
    fun inject(fragment: FavouriteMoviesFragment)
    fun inject(fragment:HostFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):AppComponent
    }
}