package com.code_chabok.coinranking.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exchange(
    val Id: String,
    val name: String,
    val url: String,
    val price: String,
    var isExpanded: Boolean
):Parcelable
