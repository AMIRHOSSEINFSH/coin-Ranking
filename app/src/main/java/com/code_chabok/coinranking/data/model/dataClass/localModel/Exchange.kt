package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.google.gson.annotations.SerializedName


@Entity
data class Exchange(
    @SerializedName("24hVolume")
    val hVolume: String,
    val coinrankingUrl: String,
    val iconUrl: String,
    val marketShare: String,
    val name: String,
    val numberOfCoins: Int,
    val numberOfMarkets: Int,
    val rank: Int,
    val recommended: Boolean,
    @PrimaryKey val uuid: String,
    val verified: Boolean,
    val description: String= "",
    val lastTickerCreatedAt: Long = 1L,
    var isExpanded: Boolean = false,
    ) {
    fun convertExchangeListModel(): ExchangeListModel {
        return ExchangeListModel(
            hVolume,
            coinrankingUrl,
            iconUrl,
            marketShare,
            name,
            numberOfCoins,
            numberOfMarkets,
            rank,
            recommended,
            uuid,
            verified
        )
    }
}
