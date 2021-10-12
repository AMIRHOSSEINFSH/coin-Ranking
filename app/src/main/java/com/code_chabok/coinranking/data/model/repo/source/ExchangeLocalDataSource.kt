package com.code_chabok.coinranking.data.model.repo.source

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange

interface ExchangeLocalDataSource {

    suspend fun insertExchange(list: List<Exchange>)

    fun getExchanges(): LiveData<List<Exchange>>

//    fun getExchange(): LiveData<Exchange>


}