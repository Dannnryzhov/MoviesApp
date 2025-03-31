package com.example.moviesapp.presentation.application

import android.app.Application
import com.example.moviesapp.di.DaggerAppComponent

class MoviesApp: Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}