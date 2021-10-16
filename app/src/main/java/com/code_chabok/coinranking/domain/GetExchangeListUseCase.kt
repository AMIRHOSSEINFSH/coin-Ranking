package com.code_chabok.coinranking.domain

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.data.model.repo.ExchangeRepositoryImp
import javax.inject.Inject

class GetExchangeListUseCase @Inject constructor(
    private val exchangeRepositoryImp: ExchangeRepositoryImp
) {
    operator fun invoke(): LiveData<Resource<List<Exchange>>> =
        exchangeRepositoryImp.getExchangeList()


}