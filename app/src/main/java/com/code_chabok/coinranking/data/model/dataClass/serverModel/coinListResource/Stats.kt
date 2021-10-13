package com.code_chabok.coinranking.data.model.dataClass.serverModel.coinListResource


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Stats(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total24hVolume")
    val total24hVolume: String,
    @SerializedName("totalCoins")
    val totalCoins: Int,
    @SerializedName("totalExchanges")
    val totalExchanges: Int,
    @SerializedName("totalMarketCap")
    val totalMarketCap: String,
    @SerializedName("totalMarkets")
    val totalMarkets: Int
) : Parcelable