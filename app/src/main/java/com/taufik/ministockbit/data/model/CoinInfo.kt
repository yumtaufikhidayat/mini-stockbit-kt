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
    val netHashesPerSecond: Int,
    @SerializedName("BlockNumber")
    val blockNumber: Int,
    @SerializedName("BlockTime")
    val blockTime: Int,
    @SerializedName("BlockReward")
    val blockReward: Int,
    @SerializedName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerializedName("MaxSupply")
    val maxSupply: Int,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("DocumentType")
    val documentType: String
): Parcelable