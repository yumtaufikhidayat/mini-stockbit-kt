package com.taufik.ministockbit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestApiResponses(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("MetaData")
    val metaData: MetaData,
    @SerializedName("SponsoredData")
    val sponsoredData: List<SponsoredData>,
    @SerializedName("Data")
    val data: List<Data>,
    @SerializedName("RateLimit")
    val rateLimit: RateLimit,
    @SerializedName("HasWarning")
    val hasWarning: Boolean
): Parcelable