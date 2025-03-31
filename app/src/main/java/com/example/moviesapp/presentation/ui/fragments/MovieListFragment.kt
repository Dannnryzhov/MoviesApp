package com.example.moviesapp.presentation.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.databinding.FragmentMovieListBinding
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.presentation.adapter.MovieAdapter
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.MovieListViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMovies()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collect { searchResults ->
                moviesAdapter.submitList(searchResults)
            }
        }
    }

    private fun observeMovies() {
        viewModel.movies
            .onEach { movies ->
                moviesAdapter.submitList(movies)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onMovieClicked(movie: MovieEntity) {
    }

    private fun onMovieLongClicked(movie: MovieEntity) {
    }
}
