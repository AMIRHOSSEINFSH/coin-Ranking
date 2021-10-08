package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource


import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("24hVolume")
    val hVolume: String,
    @SerializedName("total")
    val total: Int
)