package com.example.moviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.domain.models.MovieEntity

class MovieAdapter(
    private val onItemClick: (MovieEntity) -> Unit,
    private val onItemLongClick: (MovieEntity) -> Unit
) : PagingDataAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(MovieDiffCallBack()) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity?) {
            if (movie == null) return
            binding.tvMovieTitle.text = movie.name
            binding.tvMovieRelease.text = movie.year.toString()

            Glide.with(binding.ivMoviePoster.context)
                .load(movie.poster.url)
                .centerCrop()
                .into(binding.ivMoviePoster)

            binding.root.setOnClickListener { onItemClick(movie) }
            binding.root.setOnLongClickListener {
                onItemLongClick(movie)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

