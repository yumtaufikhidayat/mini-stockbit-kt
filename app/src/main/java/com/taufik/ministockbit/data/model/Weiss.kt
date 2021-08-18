package com.taufik.ministockbit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weiss(
    @SerializedName("Rating")
    val rating: String,
    @SerializedName("TechnologyAdoptionRating")
    val technologyAdoptionRating: String,
    @SerializedName("MarketPerformanceRating")
    val marketPerformanceRating: String
): Parcelable