package com.code_chabok.coinranking.data.model.dataClass.localModel.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.code_chabok.coinranking.data.model.dataClass.localModel.Bookmark
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin

data class CoinAndBookmark(
    @Embedded val coin: Coin,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "uuid"
    )
    var bookmark: Bookmark?

)