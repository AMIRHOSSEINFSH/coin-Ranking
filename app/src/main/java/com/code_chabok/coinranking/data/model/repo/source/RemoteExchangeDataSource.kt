package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response

class RemoteExchangeDataSource constructor(val apiService: ApiService) : ExchangeRemoteDataSource {


    override suspend fun getAllExchange(): Response<ExchangeResource> = apiService.getExchangeList()


}