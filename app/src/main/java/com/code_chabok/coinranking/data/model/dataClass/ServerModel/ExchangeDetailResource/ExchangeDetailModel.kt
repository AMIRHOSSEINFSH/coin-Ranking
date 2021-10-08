package com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeDetailResource


import com.google.gson.annotations.SerializedName

data class ExchangeDetailModel(
    @SerializedName("coinrankingUrl")
    val coinrankingUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("24hVolume")
    val hVolume: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("lastTickerCreatedAt")
    val lastTickerCreatedAt: Long,
    @SerializedName("links")
    val links: List<Link>,
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
    val verified: Boolean,
    @SerializedName("websiteUrl")
    val websiteUrl: String
)