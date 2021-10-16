package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.lifecycle.LiveData
import androidx.room.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail

@Dao
interface CoinDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCoins(coinList: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchCoins(coinList: List<Coin>)

    /*@Query("DELETE FROM coin")
    suspend fun deleteAll()*/

    @Query("SELECT * FROM coin")
    fun getCoins(): LiveData<List<Coin>>

    @Query("SELECT * FROM coin")
    fun getCoinsWithoutLiveData(): List<Coin>

    @Query("SELECT * FROM coin ORDER BY CASE WHEN :isDesc = 1 THEN price END DESC , CASE WHEN :isDesc = 0 THEN price END ASC")
    fun getPriceOrdered(isDesc: Boolean): List<Coin>

    @Query("SELECT * FROM coin ORDER BY CASE WHEN :isDesc = 1 THEN marketCap END DESC , CASE WHEN :isDesc = 0 THEN marketCap END ASC")
    fun getMarketCapOrdered(isDesc: Boolean): List<Coin>

    @Query("UPDATE Coin SET isBookmarked =:newIsBookmark WHERE uuid =:uuid ")
    suspend fun updateBookmark(uuid: String, newIsBookmark: Boolean): Int

    @Query("SELECT * FROM coin WHERE isBookmarked =1 ")
    fun getCoinsOfBookmarks(): LiveData<List<Coin>>

    @Query("SELECT uuid FROM coin WHERE isBookmarked =1 ")
    fun getBookmarksUuid(): List<String>

    @Query("SELECT * FROM coin WHERE uuid =:uuid")
    fun getDetailedCoin(uuid: String): LiveData<Coin>

    @Update
    fun updateCoin(coin: Coin)


}