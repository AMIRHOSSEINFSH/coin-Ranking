package com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeDetailResource


import com.google.gson.annotations.SerializedName

data class ExchangeDetailModel(

    val description: String,
    @SerializedName("24hVolume")
    val hVolume: String,
    val iconUrl: String,
    val name: String,
    val rank: Int,
    val uuid: String,
)