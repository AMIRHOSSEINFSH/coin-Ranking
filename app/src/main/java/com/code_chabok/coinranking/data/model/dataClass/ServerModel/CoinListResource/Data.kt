package com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource


import com.google.gson.annotations.SerializedName
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel

data class Data(
    @SerializedName("coins")
    val coinListModels: List<CoinListModel>,
    @SerializedName("stats")
    val stats: Stats
)