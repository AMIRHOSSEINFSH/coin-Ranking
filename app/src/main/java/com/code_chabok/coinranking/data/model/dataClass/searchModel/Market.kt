package com.code_chabok.coinranking.data.model.dataClass.searchModel


import com.google.gson.annotations.SerializedName

data class Market(
    @SerializedName("baseSymbol")
    val baseSymbol: String,
    @SerializedName("baseUuid")
    val baseUuid: String,
    @SerializedName("exchangeIconUrl")
    val exchangeIconUrl: String,
    @SerializedName("exchangeName")
    val exchangeName: String,
    @SerializedName("exchangeUuid")
    val exchangeUuid: String,
    @SerializedName("quoteSymbol")
    val quoteSymbol: String,
    @SerializedName("quoteUuid")
    val quoteUuid: String,
    @SerializedName("uuid")
    val uuid: String
)