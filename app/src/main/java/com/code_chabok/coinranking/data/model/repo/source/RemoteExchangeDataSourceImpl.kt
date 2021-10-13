package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeResource
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response

class RemoteExchangeDataSourceImpl constructor(val apiService: ApiService) : ExchangeRemoteDataSource {


    override suspend fun getExchangeList(): Response<ExchangeResource> = apiService.getExchangeList()


}