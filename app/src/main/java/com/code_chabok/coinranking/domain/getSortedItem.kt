package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.repo.CryptoDetailRepository
import javax.inject.Inject

class getSortedItem @Inject constructor(val repo: CryptoDetailRepository) {

    suspend operator fun invoke(
        uuid: String,
        timePeriod: String,
        ref: String
    ): Resource<CoinAndBookmark> = repo.getSortedItemBy(uuid, ref, timePeriod)
}