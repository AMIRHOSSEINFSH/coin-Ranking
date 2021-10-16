package com.code_chabok.coinranking.domain

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.repo.CryptoDetailRepository
import javax.inject.Inject

class getCryptoDetail @Inject constructor(val repo: CryptoDetailRepository) {

     operator fun invoke(uuid: String):LiveData<Resource<CoinAndBookmark>> = repo.getCoinDetailModel(uuid)
}