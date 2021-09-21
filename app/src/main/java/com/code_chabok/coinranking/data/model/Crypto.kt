package com.code_chabok.coinranking.data.model

data class Crypto(
    val name: String,
    val prev: String,
    val price: String,
    val changedRate: String,
    val isBookmark: Boolean,
    val priceUsd: String,
    val priceToPrev: String,
    var isExpanded: Boolean,
    var img_url: String,
    val Id: String
) {

}
