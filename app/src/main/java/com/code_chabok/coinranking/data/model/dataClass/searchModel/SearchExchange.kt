package com.code_chabok.coinranking.data.model.dataClass.searchModel


import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.google.gson.annotations.SerializedName

data class SearchExchange(
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("uuid")
    val uuid: String
){
    fun convertToExchange(): Exchange{
        return Exchange(
            uuid = uuid,
            name = name,
            iconUrl = iconUrl
        )
    }
}