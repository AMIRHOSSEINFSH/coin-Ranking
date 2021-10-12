package com.code_chabok.coinranking.data.model.dataClass.LocalModel

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE
import retrofit2.http.GET
import java.util.*

@Dao
interface CoinDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCoins(coinList: List<Coin>)

    @Query("DELETE FROM coin")
    suspend fun deleteAll()

    @Query("SELECT * FROM coin")
    fun getCoins(): LiveData<List<Coin>>

    @Query("SELECT * FROM coin")
    fun getCoinsWithoutLiveData(): List<Coin>

    @Query("SELECT * FROM coin ORDER BY price DESC")
    fun getPriceOrdered(): List<Coin>

    @Query("SELECT * FROM coin ORDER BY marketCap DESC")
    fun getMarketCapOrdered(): List<Coin>

    @Query("UPDATE Coin SET isBookmarked =:newIsBookmark WHERE uuid =:uuid ")
    suspend fun updateBookmark(uuid: String,newIsBookmark: Boolean): Int

    @Query("SELECT * FROM coin WHERE isBookmarked =1 ")
     fun getCoinsOfBookmarks(): LiveData<List<Coin>>

     @Query("SELECT uuid FROM coin WHERE isBookmarked =1 ")
     fun getBookmarksUuid(): List<String>
    /*@Query("SELECT * FROM coin WHERE id = :sendingId")
    suspend fun getCoin(sendingId: Int): LiveData<Coin>*/


}