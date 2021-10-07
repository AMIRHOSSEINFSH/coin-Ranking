package com.code_chabok.coinranking.di

import com.code_chabok.coinranking.data.model.dataBase.CoinRankingDataBase
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
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
    fun provideDao(db: CoinRankingDataBase): CoinDao = db.getCoinDao()


}