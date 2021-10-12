package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import retrofit2.Response

interface ExchangeRepository {

    fun getExchangeList(): LiveData<Resource<List<ExchangeListModel>>>

//    fun getExchange(id: String): LiveData<Resource<ExchangeListModel>>


}