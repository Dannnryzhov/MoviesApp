package com.example.moviesapp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PosterDto(
    @SerializedName("url") val url: String?
): Parcelable