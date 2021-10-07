package com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinDetailResource


import com.google.gson.annotations.SerializedName
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail


data class Data(
    @SerializedName("coin")
    val coin: CoinDetail
)