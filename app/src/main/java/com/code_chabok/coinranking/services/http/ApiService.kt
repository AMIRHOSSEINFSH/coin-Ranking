package com.code_chabok.coinranking.services.http

import com.code_chabok.coinranking.data.model.dataClass.SearchModel.SearchResource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinDetailResource.CoinDetailResource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource.CoinListResource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeDetailResource.ExchangeDetailResource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins")
    suspend fun getCoinList(): Response<CoinListResource>

    @GET("coin/{uuid}")
    suspend fun getDetailedCoin(@Path(value = "uuid") uuid: String): Response<CoinDetailResource>

    @GET("search-suggestions")
    suspend fun getSearchResult(@Query("query") query: String): Response<SearchResource>

    @GET("coins")
    suspend fun getListAs(
        @Query("orderBy") kindOfOrder: String? = null,
        @Query("timePeriod") timePeriod: String? = null
    ): Response<CoinListResource>


    @GET("exchanges")
    suspend fun getExchangeList(): Response<ExchangeResource>

    @GET("exchange/{uuid}")
    suspend fun getExchangeDetail(@Path(value = "uuid") uuid: String): Response<ExchangeDetailResource>

}