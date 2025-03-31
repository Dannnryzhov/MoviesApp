package com.example.moviesapp.domain.usecases

import androidx.paging.PagingData
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesPagingDataUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<MovieEntity>> {
        return repository.getPopularMoviesPagingData()
    }
}