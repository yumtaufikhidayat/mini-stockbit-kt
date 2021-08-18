package com.taufik.ministockbit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RAW(
    @SerializedName("IDR")
    val iDR: IDR
): Parcelable