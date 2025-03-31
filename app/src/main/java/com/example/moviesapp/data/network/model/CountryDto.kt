package com.example.moviesapp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryDto(
    @SerializedName("name") val country: String
): Parcelable