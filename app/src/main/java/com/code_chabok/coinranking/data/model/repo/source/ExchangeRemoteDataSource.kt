package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import retrofit2.Response

interface ExchangeRemoteDataSource {

    suspend fun getAllExchange(): Response<ExchangeResource>

}