package com.code_chabok.coinranking.data.model.dataClass.serverModel.coinDetailResource


import com.google.gson.annotations.SerializedName

data class CoinDetailResource(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)