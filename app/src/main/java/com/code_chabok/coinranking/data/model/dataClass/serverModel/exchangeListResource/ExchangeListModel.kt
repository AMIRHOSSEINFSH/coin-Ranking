package com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource


import android.os.Parcelable
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ExchangeListModel(
    @SerializedName("24hVolume")
    val hVolume: Double?,
    val numberOfMarkets: String?,
    val numberOfCoins: String?,
    val marketShare: String?,
    val iconUrl: String?,
    val name: String?,
    val rank: Int?,
    val uuid: String,
    var isExpanded: Boolean = false,
    )  {
    fun exchangeConvert(): Exchange {
        return Exchange(
            hVolume = hVolume,
            iconUrl = iconUrl,
            name = name,
            rank = rank,
            uuid = uuid,
            numberOfMarkets = numberOfMarkets,
            numberOfCoins = numberOfCoins,
            marketShare = marketShare
        )
      }
    }