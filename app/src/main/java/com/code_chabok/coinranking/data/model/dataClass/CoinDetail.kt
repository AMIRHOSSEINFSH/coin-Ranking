package com.code_chabok.coinranking.data.model.dataClass


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin

data class CoinDetail(
    //val allTimeHigh: AllTimeHigh,
    val btcPrice: String?,
    val change: String?,
    val color: String?,
    var description: String?,
    @SerializedName("24hVolume")
    val hVolume: String?,
    val iconUrl: String?,
    val lowVolume: Boolean?,
    val marketCap: Double?,
    val name: String?,
    val numberOfExchanges: Int?,
    val numberOfMarkets: Int?,
    val price: Double?,
    val rank: Int?,
    val symbol: String?,
    val tier: Int?,
    val uuid: String,
)  {
    fun convertToCoin(): Coin {
        return Coin(
            hVolume,
            uuid,
            btcPrice,
            change,
            color,
            iconUrl,
            lowVolume,
            marketCap,
            name,
            price,
            rank,
            symbol,
            tier,
            description,
            numberOfExchanges,
            numberOfMarkets
        )
    }
}