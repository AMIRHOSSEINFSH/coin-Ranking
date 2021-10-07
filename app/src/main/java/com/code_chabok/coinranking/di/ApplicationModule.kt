package com.code_chabok.coinranking.data.db

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.code_chabok.coinranking.data.model.dataBase.CoinRankingDataBase

import com.code_chabok.coinranking.services.http.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun ProvideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoinRankingDataBase =
        Room.databaseBuilder(context, CoinRankingDataBase::class.java, "CoinRankingDb").build()

}