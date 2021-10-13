package com.code_chabok.coinranking.data.model.dataClass.serverModel.coinListResource


import com.google.gson.annotations.SerializedName


data class CoinListResource(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)