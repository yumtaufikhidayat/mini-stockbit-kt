package com.taufik.ministockbit.di

import com.taufik.ministockbit.api.MiniStockbitApi
import com.taufik.ministockbit.api.UrlEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlEndpoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMiniStockbitApi(retrofit: Retrofit): MiniStockbitApi =
        retrofit.create(MiniStockbitApi::class.java)
}