package com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource


import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("24hVolume")
    val hVolume: String,
    @SerializedName("total")
    val total: Int
)