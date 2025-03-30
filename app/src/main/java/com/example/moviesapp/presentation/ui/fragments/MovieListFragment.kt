package com.example.moviesapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.databinding.FragmentMovieListBinding
import com.example.moviesapp.presentation.adapter.MovieAdapter
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.MovieListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
    }

    private val moviesAdapter: MovieAdapter by lazy {
        MovieAdapter(
            onItemClick = { movie -> TODO() },
            onItemLongClick = { movie -> TODO() }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as MoviesApp).component.inject(this)

        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMovies()
    }

    private fun setupRecyclerView() {
        binding.moviesRecyclerView.adapter = moviesAdapter
    }

    private fun observeMovies() {
        viewModel.movies
            .onEach { movies ->
                moviesAdapter.submitList(movies)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
