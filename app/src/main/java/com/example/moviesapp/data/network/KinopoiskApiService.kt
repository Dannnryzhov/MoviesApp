package com.example.moviesapp.data.network

import com.example.moviesapp.data.network.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KinopoiskApiService {
    @GET("movie")
    suspend fun getPopularMovies(
        @Query("field") field: String = "rating.kp",
        @Query("search") search: String = "7-10",
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("limit") limit: Int = 60,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5,
        @Query("query") query: String
    ): MovieResponse
}