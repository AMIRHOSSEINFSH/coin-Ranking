package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.google.gson.annotations.SerializedName

@Entity
data class Coin(
    @SerializedName("24hVolume")
    val hVolume: String? = null,
    @PrimaryKey val uuid: String,
    val btcPrice: String? = null,
    val change: String? = null,
    val color: String? = null,
    val iconUrl: String? = null,
    val lowVolume: Boolean? = null,
    val marketCap: Double? = null,
    val name: String? = null,
    val price: Double? = null,
    val rank: Int? = null,
    val symbol: String? = null,
    val tier: Int? = null,
    //val allTimeHigh: AllTimeHigh? = null,
    var description: String? = null,
    val numberOfExchanges: Int? = null,
    val numberOfMarkets: Int? = null,
    val websiteUrl: String? = null,
    var isExpanded: Boolean = false
) {

    fun convertToCoinDetail(): CoinDetail {
        return CoinDetail(
            btcPrice,
            change,
            color,
            description,
            hVolume,
            iconUrl,
            lowVolume,
            marketCap,
            name,
            numberOfExchanges,
            numberOfMarkets,
            price,
            rank,
            symbol,
            tier,
            uuid,
        )
    }
}
