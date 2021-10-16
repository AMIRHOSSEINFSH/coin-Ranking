package com.code_chabok.coinranking.data.model.dataClass.searchModel

import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.google.gson.annotations.SerializedName

data class SearchResult(

    val coins: List<CoinListModel>?,

    val exchanges: List<ExchangeListModel>?,
)