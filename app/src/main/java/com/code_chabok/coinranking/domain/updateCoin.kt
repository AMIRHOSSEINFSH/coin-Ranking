package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.repo.CryptoDetailRepository
import javax.inject.Inject

class updateCoin @Inject constructor(val repo: CryptoDetailRepository) {

    suspend operator fun invoke(uuid: String):Unit = repo.updateCoin(uuid)
}