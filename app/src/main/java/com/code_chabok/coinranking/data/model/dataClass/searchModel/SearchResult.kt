package com.code_chabok.coinranking.data.model.dataClass.searchModel

import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.google.gson.annotations.SerializedName

data class SearchResult(

    val coins: List<CoinAndBookmark>?,

    val exchanges: List<Exchange>?,
)