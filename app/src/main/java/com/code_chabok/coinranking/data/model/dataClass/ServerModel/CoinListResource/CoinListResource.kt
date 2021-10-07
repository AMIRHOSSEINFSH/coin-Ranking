package com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


data class CoinListResource(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)