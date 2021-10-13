package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel

interface ExchangeRepository {

    fun getExchangeList(): LiveData<Resource<List<ExchangeListModel>>>

//    fun getExchange(id: String): LiveData<Resource<ExchangeListModel>>


}