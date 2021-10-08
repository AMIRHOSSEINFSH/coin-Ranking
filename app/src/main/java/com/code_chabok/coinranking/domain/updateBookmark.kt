package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.repo.CoinListRepository
import javax.inject.Inject

class updateBookmark @Inject constructor(val repo: CoinListRepository) {

    suspend operator fun invoke(uuid: String,isBookmark: Boolean): Int = repo.updateBookmark(uuid,isBookmark)
}