package com.example.moviesapp.presentation.sources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.domain.models.MovieEntity
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val repository: MovieRepository,
    private val limit: Int = 60
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: 1
        return try {
            val movies = repository.getPopularMovies(page)
            val nextKey = if (movies.size < limit) null else page + 1
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
