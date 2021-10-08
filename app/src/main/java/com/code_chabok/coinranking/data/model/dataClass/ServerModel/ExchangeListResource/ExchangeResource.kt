package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource


import com.google.gson.annotations.SerializedName

data class ExchangeResource(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)