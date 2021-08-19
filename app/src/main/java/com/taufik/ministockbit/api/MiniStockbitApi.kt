package com.taufik.ministockbit.api

import com.taufik.ministockbit.data.model.MiniStockbitResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MiniStockbitApi {

    @GET(UrlEndpoint.TOTAL_TOP_TIER_VOL_FULL)
    @Headers("authorization: Apikey ${UrlEndpoint.API_KEY_1}")
    suspend fun getWatchlist(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("tsym") tsym: String
    ): MiniStockbitResponse

    @GET(UrlEndpoint.TOTAL_TOP_TIER_VOL_FULL)
    suspend fun getAllWatchlist(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("tsym") tsym: String,
        @Query("api_key") apiKey: String
    ): MiniStockbitResponse
}