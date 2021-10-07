package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.repo.CoinListRepository
import javax.inject.Inject

class getCoinDetail @Inject constructor(val repo: CoinListRepository) {

    suspend operator fun invoke(uuid: String) = repo.getDetailCoin(uuid)

}