package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExchange(Exchanges: List<Exchange>)

    @Query("SELECT * FROM Exchange")
    fun getExchanges(): LiveData<List<Exchange>>

    @Query("UPDATE exchange SET iconUrl=:iconUrl,name=:name WHERE uuid=:uuid")
    fun updateSearchExchange(uuid: String,iconUrl: String,name: String)

    @Query("SELECT * FROM EXCHANGE WHERE name LIKE '%' || :query || '%' ")
    fun getSearchedExchanges(query: String): List<Exchange>


}