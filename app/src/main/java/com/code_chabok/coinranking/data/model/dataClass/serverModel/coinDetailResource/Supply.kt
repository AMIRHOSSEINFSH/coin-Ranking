package com.code_chabok.coinranking.data.model.dataClass.serverModel.coinDetailResource


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Supply(
    @SerializedName("circulating")
    val circulating: String,
    @SerializedName("confirmed")
    val confirmed: Boolean,
    @SerializedName("total")
    val total: String
) : Parcelable