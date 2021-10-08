package com.code_chabok.coinranking.data.model.dataClass.LocalModel

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.GET
import java.util.*

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetCoins(coinList: List<Coin>)

    @Query("SELECT * FROM coin")
    fun getCoins(): LiveData<List<Coin>>

    @Query("UPDATE Coin SET isBookmarked =:newIsBookmark WHERE uuid =:uuid ")
    suspend fun updateBookmark(uuid: String,newIsBookmark: Boolean): Int

    @Query("SELECT * FROM coin WHERE isBookmarked =1")
    suspend fun getCoinsOfBookmarks(): LiveData<List<Coin>>
    /*@Query("SELECT * FROM coin WHERE id = :sendingId")
    suspend fun getCoin(sendingId: Int): LiveData<Coin>*/


}