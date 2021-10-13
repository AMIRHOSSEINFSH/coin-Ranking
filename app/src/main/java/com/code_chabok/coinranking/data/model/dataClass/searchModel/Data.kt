package com.code_chabok.coinranking.data.model.dataClass.searchModel


import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.google.gson.annotations.SerializedName
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel


data class Data(
    @SerializedName("coins")
    val coins: List<CoinListModel>,
    @SerializedName("exchanges")
    val exchanges: List<ExchangeListModel>,
    @SerializedName("markets")
    val markets: List<Market>
)