package com.code_chabok.coinranking.data.model.dataClass.LocalModel

import androidx.room.Entity
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeListModel
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
    val uuid: String,
    val verified: Boolean,
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
