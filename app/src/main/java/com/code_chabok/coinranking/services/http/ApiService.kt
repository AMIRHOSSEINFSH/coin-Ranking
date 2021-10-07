package com.code_chabok.coinranking.services.http

import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinDetailResource.CoinDetailResource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource.CoinListResource
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins")
    suspend fun getCoinList(): Response<CoinListResource>

    @GET("coin/{uuid}")
    suspend fun getDetailedCoin(@Path(value = "uuid") uuid: String): Response<CoinDetailResource>

}