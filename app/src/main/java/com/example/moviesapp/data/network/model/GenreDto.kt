package com.example.moviesapp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreDto(
    @SerializedName("name") val genre: String
): Parcelable