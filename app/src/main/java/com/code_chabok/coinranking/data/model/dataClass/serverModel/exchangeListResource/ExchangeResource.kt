package com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource


import com.google.gson.annotations.SerializedName

data class ExchangeResource(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)