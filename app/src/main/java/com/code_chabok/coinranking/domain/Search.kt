package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.repo.SearchRepository
import javax.inject.Inject

class Search @Inject constructor(val repo: SearchRepository) {

    suspend operator fun invoke(query: String): List<CoinListModel> = repo.search(query)
}