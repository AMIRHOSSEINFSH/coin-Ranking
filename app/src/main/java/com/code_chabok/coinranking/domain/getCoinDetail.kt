package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.repo.CoinListRepository
import com.code_chabok.coinranking.data.model.repo.SearchRepository
import com.code_chabok.coinranking.feature.home.HomeFragment
import javax.inject.Inject

class getCoinDetail @Inject constructor(val repo: SearchRepository) {

    suspend operator fun invoke(uuid: String) = repo.getDetailCoin(uuid)

}