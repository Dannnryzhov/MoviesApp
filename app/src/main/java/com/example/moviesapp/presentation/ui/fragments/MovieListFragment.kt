package com.example.moviesapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMovieListBinding
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.presentation.adapter.MovieAdapter
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.MovieListViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater, container, false)
    }

    override fun injectDependencies() {
        (requireActivity().application as MoviesApp).component.inject(this)
    }

    private val moviesAdapter: MovieAdapter by lazy {
        MovieAdapter(
            onItemClick = { movie -> onMovieClicked(movie) },
            onItemLongClick = { movie -> onMovieLongClicked(movie) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePopularMovies()
        observeFavoriteMovies()
        setupSearch()
    }

    private fun setupRecyclerView() {
        binding.moviesRecyclerView.adapter = moviesAdapter
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable?.toString() ?: ""
            viewModel.search(query)
        }
    }

    private fun observeFavoriteMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteMovies.collect { favorites ->
                moviesAdapter.updateFavoriteMovies(favorites)
            }
        }
    }

    private fun observePopularMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest { pagingData ->
                moviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun onMovieClicked(movie: MovieEntity) {
        val bundle = Bundle().apply {
            putString("sourceFragment", "list")
            putInt("movieId", movie.id)
            putString("movieName", movie.name ?: "Неизвестное название")
            putString("movieDescription", movie.description ?: "Нет описания")
            putString("moviePosterUrl", movie.poster.url ?: "")
            putString("movieGenres", movie.genres.joinToString(", ") { it.genre })
            putString("movieCountries", movie.countries.joinToString(", ") { it.country })
        }
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, bundle)
    }

    private fun onMovieLongClicked(movie: MovieEntity) {
        viewModel.addToFavouriteMovie(movie)
    }
}
