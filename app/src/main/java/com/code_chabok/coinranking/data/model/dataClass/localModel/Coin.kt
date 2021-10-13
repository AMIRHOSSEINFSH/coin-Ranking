package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.google.gson.annotations.SerializedName

@Entity
data class Coin(
    //@PrimaryKey(autoGenerate = true) val id: Int? = null,
    @SerializedName("24hVolume")
    val hVolume: String,
    @PrimaryKey val uuid: String,
    val btcPrice: String,
    val change: String,
    val coinrankingUrl: String,
    val color: String? ,
    val iconUrl: String,
    val listedAt: Int,
    val lowVolume: Boolean,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val rank: Int,
    val symbol: String,
    val tier: Int,
    var isBookmarked: Boolean = false,

    //val allTimeHigh: AllTimeHigh? = null,
    val description: String?= null,
    val numberOfExchanges: Int? = null,
    val numberOfMarkets: Int? = null,
    //val supply: Supply? = null,
    val websiteUrl: String? = null
) {
    fun convertToCoinList(): CoinListModel {
        return CoinListModel(
            hVolume,
            btcPrice,
            change,
            coinrankingUrl,
            color,
            iconUrl,
            listedAt,
            lowVolume,
            marketCap,
            name,
            price,
            rank,
            symbol,
            tier,
            uuid,
            isBookmarked = isBookmarked
        )
    }
}
