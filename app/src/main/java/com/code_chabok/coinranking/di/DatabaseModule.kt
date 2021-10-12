package com.code_chabok.coinranking.di

import android.content.Context
import androidx.room.Room
import com.code_chabok.coinranking.data.model.dataBase.CoinRankingDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoinRankingDataBase =
        Room.databaseBuilder(context, CoinRankingDataBase::class.java, "CoinRankingDataBase").build()

}