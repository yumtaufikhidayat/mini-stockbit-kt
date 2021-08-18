package com.taufik.ministockbit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IDR(
    @SerializedName("TYPE")
    val type: String,
    @SerializedName("MARKET")
    val market: String,
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    val toSymbol: String,
    @SerializedName("FLAGS")
    val flags: String,
    @SerializedName("PRICE")
    val price: Double,
    @SerializedName("LASTUPDATE")
    val lastUpdate: Int,
    @SerializedName("MEDIAN")
    val median: Double,
    @SerializedName("LASTVOLUME")
    val lastVolume: Int,
    @SerializedName("LASTVOLUMETO")
    val lastVolumeTo: Double,
    @SerializedName("LASTTRADEID")
    val lastTradeId: String,
    @SerializedName("VOLUMEDAY")
    val volumeDay: Int,
    @SerializedName("VOLUMEDAYTO")
    val volumeDayTo: Int,
    @SerializedName("VOLUME24HOUR")
    val volume24Hour: Int,
    @SerializedName("VOLUME24HOURTO")
    val volume24HourTo: Int,
    @SerializedName("OPENDAY")
    val openDay: Double,
    @SerializedName("HIGHDAY")
    val highDay: Double,
    @SerializedName("LOWDAY")
    val lowDay: Double,
    @SerializedName("OPEN24HOUR")
    val open24Hour: Double,
    @SerializedName("HIGH24HOUR")
    val high24Hour: Double,
    @SerializedName("LOW24HOUR")
    val low24Hour: Double,
    @SerializedName("LASTMARKET")
    val lastMarket: String,
    @SerializedName("VOLUMEHOUR")
    val volumeHour: Int,
    @SerializedName("VOLUMEHOURTO")
    val volumeHourTo: Int,
    @SerializedName("OPENHOUR")
    val openHour: Double,
    @SerializedName("HIGHHOUR")
    val highHour: Double,
    @SerializedName("LOWHOUR")
    val lowHour: Double,
    @SerializedName("TOPTIERVOLUME24HOUR")
    val topTierVolume24Hour: Int,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    val topTierVolume24HourTo: Int,
    @SerializedName("CHANGE24HOUR")
    val change24Hour: Double,
    @SerializedName("CHANGEPCT24HOUR")
    val changePct24Hour: Double,
    @SerializedName("CHANGEDAY")
    val changeDay: Double,
    @SerializedName("CHANGEPCTDAY")
    val changePctDay: Double,
    @SerializedName("CHANGEHOUR")
    val changeHour: Double,
    @SerializedName("CHANGEPCTHOUR")
    val changePctHour: Double,
    @SerializedName("CONVERSIONTYPE")
    val conversionType: String,
    @SerializedName("CONVERSIONSYMBOL")
    val conversionSymbol: String,
    @SerializedName("SUPPLY")
    val supply: Int,
    @SerializedName("MKTCAP")
    val mktCap: Int,
    @SerializedName("MKTCAPPENALTY")
    val mktCapPenalty: Int,
    @SerializedName("TOTALVOLUME24H")
    val totalVolume24H: Int,
    @SerializedName("TOTALVOLUME24HTO")
    val totalVolume24HTo: Int,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    val totalTopTierVolume24H: Int,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    val totalTopTierVolume24HTo: Int,
    @SerializedName("IMAGEURL")
    val imageUrl: String
): Parcelable