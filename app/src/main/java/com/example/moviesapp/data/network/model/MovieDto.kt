package com.example.moviesapp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("year") val year: Int,
    @SerializedName("countries") val countries: List<CountryDto>,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("poster") val poster: PosterDto
): Parcelable
