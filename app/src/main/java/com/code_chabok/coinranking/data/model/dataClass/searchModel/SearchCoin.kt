package com.code_chabok.coinranking.data.model.dataClass.searchModel


import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
import com.google.gson.annotations.SerializedName

data class SearchCoin(
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("uuid")
    val uuid: String
){
    fun convertToCoin():Coin{
        return Coin(
            iconUrl = iconUrl,
            name = name,
            symbol = symbol,
            uuid = uuid
        )
    }
}