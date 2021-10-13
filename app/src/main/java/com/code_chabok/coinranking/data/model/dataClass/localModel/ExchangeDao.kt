package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExchange(Exchanges: List<Exchange>)

    @Query("SELECT * FROM Exchange")
    fun getExchanges(): LiveData<List<Exchange>>

//    @Query("SELECT * FROM Exchange WHERE uuid = :_uuid")
//    fun getExchange(_uuid: String): LiveData<Exchange>


}