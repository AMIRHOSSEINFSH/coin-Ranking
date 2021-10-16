package com.code_chabok.coinranking.data.model.dataClass


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Ignore
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
@Parcelize
data class CoinListModel(
    @SerializedName("24hVolume")
    val hVolume: String = "",
    val btcPrice: String= "",
    val change: String? = "null",
    val color: String? = "null",
    val iconUrl: String= "",
    val lowVolume: Boolean = false,
    val marketCap: Double = 1.2,
    val name: String= "",
    @SerializedName("price")
    val price: Double = 1.2,
    val rank: Int = 1,
    @SerializedName("symbol")
    val symbol: String= "",
    val tier: Int = 1,
    val uuid: String= "",
    var isExpanded: Boolean = false,
    @Ignore
    var isBookmarked: Boolean =false
): Parcelable {
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
            isBookmarked = isBookmarked
        )
    }
}