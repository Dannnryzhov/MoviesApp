package com.example.moviesapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesapp.domain.models.MovieEntity

class MovieDiffCallBack : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
        oldItem == newItem
}
