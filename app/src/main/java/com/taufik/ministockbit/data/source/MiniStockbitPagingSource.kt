package com.taufik.ministockbit.data.source

import androidx.paging.PagingSource
import com.taufik.ministockbit.api.MiniStockbitApi
import com.taufik.ministockbit.api.UrlEndpoint
import com.taufik.ministockbit.data.model.Data
import retrofit2.HttpException
import java.io.IOException

class MiniStockbitPagingSource(
    private val miniStockbitApi: MiniStockbitApi
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val position = params.key ?: UrlEndpoint.STARTING_PAGE_INDEX

        return try {
            val response = miniStockbitApi.getAllWatchlist(
                UrlEndpoint.LIMIT_PAGE,
                position,
                UrlEndpoint.TSYM,
                UrlEndpoint.API_KEY_1
            )
            val stocks = response.data
            LoadResult.Page(
                data = stocks,
                prevKey = if (position == UrlEndpoint.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (stocks.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}