package com.example.moviesapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.databinding.FragmentFavouriteMoviesBinding
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.presentation.adapter.MovieAdapter
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.FavouriteMoviesViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavouriteMovies()
        setupSearch()
    }

    private fun setupRecyclerView() {
        binding.moviesRecyclerView.adapter = favouriteMoviesAdapter
    }

    private fun observeFavouriteMovies() {
        viewModel.favouriteMovies
            .onEach { movies ->
                favouriteMoviesAdapter.submitList(movies)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable?.toString() ?: ""
            viewModel.search(query)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collect { searchResults ->
                favouriteMoviesAdapter.submitList(searchResults)
            }
        }
    }

    private fun onMovieClicked(movie: MovieEntity) {
    }


    private fun onMovieLongClicked(movie: MovieEntity) {
        viewModel.removeFavouriteMovie(movie)
    }
}
