package com.code_chabok.coinranking.services.http

import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.searchModel.SearchResource
import com.code_chabok.coinranking.data.model.dataClass.serverModel.coinDetailResource.CoinDetailResource
import com.code_chabok.coinranking.data.model.dataClass.serverModel.coinListResource.CoinListResource
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeDetailResource.ExchangeDetailResource
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeResource
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

    @GET("coin/{uuid}")
    suspend fun getDetailCoinAs(
        @Path(value = "uuid") nowCurr: String,
        @Query("referenceCurrencyUuid") ref: String,
        @Query("timePeriod") timePeriod: String
    ):Response<CoinDetailResource>


    @GET("exchanges")
    suspend fun getExchangeList(): Response<ExchangeResource>

    @GET("exchange/{uuid}")
    suspend fun getExchangeDetail(@Path(value = "uuid") uuid: String): Response<ExchangeDetailResource>

}