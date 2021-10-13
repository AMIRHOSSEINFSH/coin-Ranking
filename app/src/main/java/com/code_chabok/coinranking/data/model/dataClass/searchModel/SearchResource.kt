package com.code_chabok.coinranking.data.model.dataClass.searchModel


import com.google.gson.annotations.SerializedName


data class SearchResource(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)