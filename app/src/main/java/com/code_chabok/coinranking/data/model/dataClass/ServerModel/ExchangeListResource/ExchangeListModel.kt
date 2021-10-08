package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource


import com.google.gson.annotations.SerializedName

data class ExchangeListModel(
    @SerializedName("coinrankingUrl")
    val coinrankingUrl: String,
    @SerializedName("24hVolume")
    val hVolume: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("marketShare")
    val marketShare: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("numberOfCoins")
    val numberOfCoins: Int,
    @SerializedName("numberOfMarkets")
    val numberOfMarkets: Int,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("recommended")
    val recommended: Boolean,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("verified")
    val verified: Boolean
)