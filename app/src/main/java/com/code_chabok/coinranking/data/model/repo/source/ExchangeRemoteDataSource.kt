package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeResource
import retrofit2.Response

interface ExchangeRemoteDataSource {

    suspend fun getExchangeList(): Response<ExchangeResource>

}