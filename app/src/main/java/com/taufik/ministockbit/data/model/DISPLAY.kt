package com.taufik.ministockbit.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DISPLAY(
    @SerializedName("IDR")
    val idr: IDRX
): Parcelable