package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Bookmark(
    @PrimaryKey val uuid: String,
    var isNotify: Boolean = false
)
