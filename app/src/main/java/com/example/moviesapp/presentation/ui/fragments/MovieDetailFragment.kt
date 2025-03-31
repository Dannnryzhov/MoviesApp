package com.example.moviesapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.domain.models.CountryEntity
import com.example.moviesapp.domain.models.GenreEntity
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.MovieDetailViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: MovieDetailViewModel by viewModels { viewModelFactory }

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieDetailBinding {
        return FragmentMovieDetailBinding.inflate(inflater, container, false)
    }

    override fun injectDependencies() {
        (requireActivity().application as MoviesApp).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieName = args.movieName
        val movieDescription = args.movieDescription
        val moviePosterUrl = args.moviePosterUrl

        val movieGenres = args.movieGenres.split(", ").map { GenreEntity(it) }
        val movieCountries = args.movieCountries.split(", ").map { CountryEntity(it) }

        updateUI(movieName, movieDescription, movieGenres, movieCountries, moviePosterUrl)
    }

    private fun updateUI(name: String, description: String, genres: List<GenreEntity>, countries: List<CountryEntity>, posterUrl: String) {
        binding.tvTitle.text = name
        binding.tvDescription.text = description

        val genreNames = genres.joinToString(", ") { it.genre }
        val countryNames = countries.joinToString(", ") { it.country }

        binding.tvGenresCountriesYear.text = "Жанры: $genreNames\nСтраны: $countryNames"

        Glide.with(this)
            .load(posterUrl)
            .centerCrop()
            .into(binding.ivPoster)
    }

}
