package com.code_chabok.coinranking.data.model.dataClass.LocalModel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCoins(coinList: List<Coin>)

    @Query("SELECT * FROM coin")
     fun getCoins(): LiveData<List<Coin>>

    /*@Query("SELECT * FROM coin WHERE id = :sendingId")
    suspend fun getCoin(sendingId: Int): LiveData<Coin>*/


}