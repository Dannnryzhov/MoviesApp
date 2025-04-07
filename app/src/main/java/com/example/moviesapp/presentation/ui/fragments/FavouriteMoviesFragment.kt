package com.example.moviesapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentFavouriteMoviesBinding
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.presentation.adapter.MovieAdapter
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.FavouriteMoviesViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesFragment : BaseFragment<FragmentFavouriteMoviesBinding, FavouriteMoviesViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: FavouriteMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouriteMoviesViewModel::class.java]
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavouriteMoviesBinding {
        return FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
    }

    override fun injectDependencies() {
        (requireActivity().application as MoviesApp).component.inject(this)
    }

    private val favouriteMoviesAdapter: MovieAdapter by lazy {
        MovieAdapter(
            onItemClick = { movie -> onMovieClicked(movie) },
            onItemLongClick = { movie -> onMovieLongClicked(movie) }
        )
    }

    private var currentCollectionJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        observeFavouriteMovies()
    }

    private fun setupRecyclerView() {
        binding.moviesRecyclerView.adapter = favouriteMoviesAdapter
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable?.toString() ?: ""
            currentCollectionJob?.cancel()
            if (query.isBlank()) {
                observeFavouriteMovies()
            } else {
                viewModel.search(query)
                observeSearchResults()
            }
        }
    }

    private fun observeFavouriteMovies() {
        currentCollectionJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favouriteMovies.collectLatest { movies ->
                favouriteMoviesAdapter.submitData(lifecycle, PagingData.from(movies))
            }
        }
    }

    private fun observeSearchResults() {
        currentCollectionJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collectLatest { results ->
                favouriteMoviesAdapter.submitData(lifecycle, PagingData.from(results))
            }
        }
    }

    private fun onMovieClicked(movie: MovieEntity) {
        val bundle = Bundle().apply {
            putString("sourceFragment", "favorites")
            putInt("movieId", movie.id)
            putString("movieName", movie.name ?: "Неизвестное название")
            putString("movieDescription", movie.description ?: "Нет описания")
            putString("moviePosterUrl", movie.poster.url ?: "")
            putString("movieGenres", movie.genres.joinToString(", ") { it.genre })
            putString("movieCountries", movie.countries.joinToString(", ") { it.country })
        }
        findNavController().navigate(R.id.action_favouriteMoviesFragment_to_movieDetailFragment, bundle)
    }

    private fun onMovieLongClicked(movie: MovieEntity) {
        viewModel.removeFavouriteMovie(movie)
    }
}

