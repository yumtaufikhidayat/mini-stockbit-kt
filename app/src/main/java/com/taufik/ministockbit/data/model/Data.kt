package com.taufik.ministockbit.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfo,
    @SerializedName("RAW")
    val raw: RAW,
    @SerializedName("DISPLAY")
    val display: DISPLAY
): Parcelable