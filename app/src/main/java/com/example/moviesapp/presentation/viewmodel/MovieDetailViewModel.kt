package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _movieName = MutableLiveData<String>()
    val movieName: LiveData<String> get() = _movieName

    private val _movieDescription = MutableLiveData<String>()
    val movieDescription: LiveData<String> get() = _movieDescription

    private val _moviePosterUrl = MutableLiveData<String>()
    val moviePosterUrl: LiveData<String> get() = _moviePosterUrl

    private val _movieGenres = MutableLiveData<String>()
    val movieGenres: LiveData<String> get() = _movieGenres

    private val _movieCountries = MutableLiveData<String>()
    val movieCountries: LiveData<String> get() = _movieCountries

    fun setMovieDetails(name: String, description: String, posterUrl: String, genres: String, countries: String) {
        _movieName.value = name
        _movieDescription.value = description
        _moviePosterUrl.value = posterUrl
        _movieGenres.value = genres
        _movieCountries.value = countries
    }
}
