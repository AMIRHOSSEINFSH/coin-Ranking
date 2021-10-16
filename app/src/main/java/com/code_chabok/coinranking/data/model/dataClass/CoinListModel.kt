package com.code_chabok.coinranking.data.model.dataClass


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Ignore
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
data class CoinListModel(
    @SerializedName("24hVolume")
    val hVolume: String?,
    val btcPrice: String?,
    val change: String? =null,
    val coinrankingUrl: String?,
    val color: String?,
    val iconUrl: String?,
    val listedAt: Int?,
    val lowVolume: Boolean?,
    val marketCap: Double?,
    val name: String?,
    val price: Double?,
    val rank: Int?,
    val symbol: String?,
    val tier: Int?,
    val uuid: String,
    var isExpanded: Boolean = false,
) {
    fun convertToCoin(): Coin {
        return Coin(
            hVolume = hVolume,
            btcPrice = btcPrice,
            change = change,
            color = color,
            iconUrl = iconUrl,
            lowVolume = lowVolume,
            marketCap = marketCap,
            name = name,
            price = price,
            rank = rank,
            symbol = symbol,
            tier = tier,
            uuid = uuid,
        )
    }

}