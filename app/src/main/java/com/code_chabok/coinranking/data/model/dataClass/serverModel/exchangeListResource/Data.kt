package com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("exchanges")
    val exchangeListModels: List<ExchangeListModel>,
    @SerializedName("stats")
    val stats: Stats
)