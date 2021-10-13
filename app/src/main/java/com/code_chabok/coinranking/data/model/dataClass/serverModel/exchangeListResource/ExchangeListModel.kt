package com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource


import android.os.Parcelable
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
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
    val verified: Boolean,
    var isExpanded: Boolean = false,
    ) : Parcelable {
    fun exchangeConvert(): Exchange {
        return Exchange(
            hVolume = hVolume,
            coinrankingUrl = coinrankingUrl,
            iconUrl = iconUrl,
            marketShare = marketShare,
            name = name,
            numberOfCoins = numberOfCoins,
            numberOfMarkets = numberOfMarkets,
            rank = rank,
            recommended = recommended,
            uuid = uuid,
            verified = verified
        )
      }
    }