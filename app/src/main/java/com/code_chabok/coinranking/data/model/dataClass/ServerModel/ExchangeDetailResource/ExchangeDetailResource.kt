package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeDetailResource


import com.google.gson.annotations.SerializedName

data class ExchangeDetailResource(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)