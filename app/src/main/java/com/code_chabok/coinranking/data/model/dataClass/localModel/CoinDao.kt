package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.lifecycle.LiveData
import androidx.room.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.searchModel.SearchCoin

@Dao
interface CoinDao {

    @Query("UPDATE coin SET iconUrl=:iconUrl,name=:name WHERE uuid=:uuid")
    fun updateSearchCoin(uuid: String,iconUrl: String,name: String)

    @Query("SELECT * FROM coin WHERE name LIKE '%'|| :query || '%' ")
    fun getSearchedCoins(query: String): List<CoinAndBookmark>

    @Transaction
    @Query("SELECT * FROM coin")
    fun getCoinsAndBookMarks(): LiveData<List<CoinAndBookmark>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetCoins(coinList: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoin(coin: Coin)

    @Update
    fun updateCoin(coin: Coin)

    @Query("SELECT * FROM coin")
    fun getCoins(): LiveData<List<Coin>>

    @Transaction
    @Query("SELECT * FROM coin ORDER BY price =:orderDirection")
    fun getPriceOrdered(orderDirection: String): List<CoinAndBookmark>

    @Transaction
    @Query("SELECT * FROM coin ORDER BY marketCap =:orderDirection")
    fun getMarketCapOrdered(orderDirection: String): List<CoinAndBookmark>

    @Query("SELECT * FROM coin WHERE uuid =:uuid")
    fun getDetailedCoin(uuid: String): LiveData<CoinAndBookmark>

    @Transaction
    @Query("SELECT * FROM coin WHERE uuid =:uuid")
    fun getCoin(uuid: String):CoinAndBookmark



}