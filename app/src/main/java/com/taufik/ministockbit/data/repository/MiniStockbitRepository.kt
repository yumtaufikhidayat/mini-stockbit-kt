package com.taufik.ministockbit.data.repository

import com.taufik.ministockbit.api.MiniStockbitApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MiniStockbitRepository @Inject constructor(private val miniStockbitApi: MiniStockbitApi) {

}