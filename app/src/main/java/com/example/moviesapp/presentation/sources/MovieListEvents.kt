package com.example.moviesapp.presentation.sources

sealed interface MovieListEvents {
    data class ShowMovieListDialog(val message: String) : MovieListEvents
}
