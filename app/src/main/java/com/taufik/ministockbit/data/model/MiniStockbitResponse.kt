package com.taufik.ministockbit.data.model

import com.google.gson.annotations.SerializedName

data class MiniStockbitResponse(
    @SerializedName("Data")
    val data: List<Data>
)