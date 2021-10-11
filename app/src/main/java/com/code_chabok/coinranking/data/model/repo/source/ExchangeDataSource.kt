package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.Exchange
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import retrofit2.Response

interface ExchangeDataSource {

   suspend fun getAllExchange(): Response<ExchangeResource>

    fun insertList(list: List<Exchange>)

}