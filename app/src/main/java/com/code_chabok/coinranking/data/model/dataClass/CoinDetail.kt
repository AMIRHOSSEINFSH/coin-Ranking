package com.code_chabok.coinranking.data.model.dataClass


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class CoinDetail(
    //val allTimeHigh: AllTimeHigh,
    //@SerializedName("btcPrice")
    val btcPrice: String,
    //@SerializedName("change")
    val change: String,
    //@SerializedName("coinrankingUrl")
    val coinrankingUrl: String,
    //@SerializedName("color")
    val color: String,
    //@SerializedName("description")
    val description: String,
    @SerializedName("24hVolume")
    val hVolume: String,
    //@SerializedName("iconUrl")
    val iconUrl: String,
    //val links: List<Link>,
    //@SerializedName("lowVolume")
    val lowVolume: Boolean,
    //@SerializedName("marketCap")
    val marketCap: Double,
    //@SerializedName("name")
    val name: String,
    //@SerializedName("numberOfExchanges")
    val numberOfExchanges: Int,
    //@SerializedName("numberOfMarkets")
    val numberOfMarkets: Int,
    //@SerializedName("price")
    val price: Double,
    //@SerializedName("rank")
    val rank: Int,
    //val sparkline: List<String>,
    //val supply: Supply,
    //@SerializedName("symbol")
    val symbol: String,
    //@SerializedName("tier")
    val tier: Int,
    //@SerializedName("uuid")
    val uuid: String,
    //@SerializedName("websiteUrl")
    val websiteUrl: String
):Parcelable