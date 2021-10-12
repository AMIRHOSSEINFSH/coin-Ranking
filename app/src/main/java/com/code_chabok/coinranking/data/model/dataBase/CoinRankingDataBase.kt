package com.code_chabok.coinranking.data.model.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.ExchangeDao

@Database(
    entities =
    [Coin::class, Exchange::class],
    version = 1,
    exportSchema = false
)
abstract class CoinRankingDataBase : RoomDatabase() {

    abstract fun getCoinDao(): CoinDao

    abstract fun getExchangeDao(): ExchangeDao
}
