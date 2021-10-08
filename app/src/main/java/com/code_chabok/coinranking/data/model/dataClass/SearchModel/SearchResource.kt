package com.code_chabok.coinranking.data.model.dataClass.SearchModel


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


data class SearchResource(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)