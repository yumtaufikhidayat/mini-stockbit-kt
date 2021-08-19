package com.taufik.ministockbit.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.taufik.ministockbit.api.MiniStockbitApi
import com.taufik.ministockbit.data.source.MiniStockbitPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MiniStockbitRepository @Inject constructor(private val miniStockbitApi: MiniStockbitApi) {
    fun getAllWatchlistResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MiniStockbitPagingSource(miniStockbitApi) }
        ).liveData
}