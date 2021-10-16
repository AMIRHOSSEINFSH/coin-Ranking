package com.code_chabok.coinranking.data.model.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.code_chabok.coinranking.data.model.dataClass.localModel.*

@Database(
    entities =
    [Coin::class, Exchange::class, Bookmark::class],
    version = 1,
    exportSchema = false
)
abstract class CoinRankingDataBase : RoomDatabase() {

    abstract fun getCoinDao(): CoinDao

    abstract fun getExchangeDao(): ExchangeDao

    abstract fun getBookmarkDao(): BookmarkDao
}
