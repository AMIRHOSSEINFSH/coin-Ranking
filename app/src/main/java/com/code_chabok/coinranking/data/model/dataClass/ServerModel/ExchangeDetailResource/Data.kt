package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeDetailResource


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("exchange")
    val exchange: ExchangeDetailModel
)