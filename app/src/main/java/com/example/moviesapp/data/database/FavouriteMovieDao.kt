package com.example.moviesapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMovieDao {

    @Transaction
    suspend fun insertFavoriteMovieWithDetails(
        favourite: MovieDbEntity,
        countries: List<CountryDbEntity>,
        genres: List<GenreDbEntity>
    ) {
        insertFavourites(favourite)
        insertCountries(countries)
        insertGenres(genres)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourites(favourite: MovieDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreDbEntity>)

    @Transaction
    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouritesWithDetails(): Flow<List<FavouriteMovieWithDetailsEntity>>

    @Transaction
    suspend fun deleteFavouriteMovieWithDetails(favourite: MovieDbEntity) {
        deleteFavourite(favourite)
        deleteCountries(favourite.id)
        deleteGenres(favourite.id)
    }

    @Delete
    suspend fun deleteFavourite(favourite: MovieDbEntity)

    @Query("DELETE FROM movie_countries WHERE movieId = :movieId")
    suspend fun deleteCountries(movieId: Int)

    @Query("DELETE FROM movie_genres WHERE movieId = :movieId")
    suspend fun deleteGenres(movieId: Int)
}