package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("exchanges")
    val exchangeListModels: List<ExchangeListModel>,
    @SerializedName("stats")
    val stats: Stats
)