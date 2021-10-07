package com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinDetailResource


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

data class CoinDetailResource(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)