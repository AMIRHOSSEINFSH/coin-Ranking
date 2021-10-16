package com.code_chabok.coinranking.domain

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.repo.CoinListRepository
import javax.inject.Inject

class getListOfCoins @Inject constructor(val repo: CoinListRepository) {

    operator fun invoke(): LiveData<Resource<List<CoinAndBookmark>>> = repo.getListOfCoin()


}