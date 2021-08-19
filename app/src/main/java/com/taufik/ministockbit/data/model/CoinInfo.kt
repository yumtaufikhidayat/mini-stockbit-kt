package com.taufik.ministockbit.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinInfo(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Internal")
    val internal: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Url")
    val url: String,
    @SerializedName("Algorithm")
    val algorithm: String,
    @SerializedName("ProofType")
    val proofType: String,
    @SerializedName("Rating")
    val rating: Rating,
    @SerializedName("NetHashesPerSecond")
    val netHashesPerSecond: Double,
    @SerializedName("BlockNumber")
    val blockNumber: Double,
    @SerializedName("BlockTime")
    val blockTime: Double,
    @SerializedName("BlockReward")
    val blockReward: Double,
    @SerializedName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerializedName("MaxSupply")
    val maxSupply: Double,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("DocumentType")
    val documentType: String
): Parcelable