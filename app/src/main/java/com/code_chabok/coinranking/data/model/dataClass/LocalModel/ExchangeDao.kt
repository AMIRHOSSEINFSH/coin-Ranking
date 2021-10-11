package com.code_chabok.coinranking.data.model.dataClass.LocalModel

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.GET
import java.util.*

@Dao
interface ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExchange(Exchanges: List<Exchange>)

    @Query("SELECT * FROM Exchange")
    fun getExchange(): LiveData<List<Exchange>>


}