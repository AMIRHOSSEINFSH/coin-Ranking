package com.code_chabok.coinranking.di

import com.code_chabok.coinranking.data.model.dataBase.CoinRankingDataBase
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.ExchangeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    @Provides
    @Singleton
    fun provideCoinDao(db: CoinRankingDataBase): CoinDao = db.getCoinDao()

    @Provides
    @Singleton
    fun provideExchangeDao(db: CoinRankingDataBase): ExchangeDao = db.getExchangeDao()

}