package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.google.gson.annotations.SerializedName

@Entity
data class Coin(
    //@PrimaryKey(autoGenerate = true) val id: Int? = null,
    @SerializedName("24hVolume")
    val hVolume: String,
    @PrimaryKey val uuid: String,
    val btcPrice: String,
    val change: String? =null,
    val color: String? ,
    val iconUrl: String,
    val lowVolume: Boolean,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val rank: Int,
    val symbol: String,
    val tier: Int,
    var isBookmarked: Boolean = false,

    //val allTimeHigh: AllTimeHigh? = null,
    var description: String?= null,
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
            color,
            iconUrl,
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

    fun convertToCoinDetail(): CoinDetail{
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
            isBookmarked
        )
    }
}
