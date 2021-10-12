package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.repo.CoinListRepository
import com.code_chabok.coinranking.data.model.repo.SearchRepository
import javax.inject.Inject

class updateBookmark @Inject constructor(val repo: SearchRepository) {

    suspend operator fun invoke(uuid: String,isBookmark: Boolean): Int = repo.updateBookmark(uuid,isBookmark)
}