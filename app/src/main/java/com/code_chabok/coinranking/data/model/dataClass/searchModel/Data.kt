package com.code_chabok.coinranking.data.model.dataClass.searchModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("coins")
    val searchCoins: List<SearchCoin>,
    @SerializedName("exchanges")
    val searchExchanges: List<SearchExchange>,
    @SerializedName("markets")
    val markets: List<Market>
)