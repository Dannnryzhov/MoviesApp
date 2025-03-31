package com.example.moviesapp.di

import android.app.Application
import com.example.moviesapp.presentation.ui.activity.MainActivity
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
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: MovieListFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):AppComponent
    }
}