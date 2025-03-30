package com.example.moviesapp.di

import android.app.Application
import com.example.moviesapp.MainActivity
import dagger.BindsInstance
import dagger.Component
import jakarta.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):AppComponent
    }
}