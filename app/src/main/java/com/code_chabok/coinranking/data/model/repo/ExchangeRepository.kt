package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.NetworkBoundResource
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.localModel.ExchangeDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.serverModel.coinListResource.CoinListResource
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeResource
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response
import javax.inject.Inject

class ExchangeRepository @Inject constructor(val apiService: ApiService, val exchangeDao: ExchangeDao) {

    fun getExchanges(uuid: String): LiveData<Resource<Exchange>> =

        object : NetworkBoundResource<Exchange, ExchangeResource>() {
            override suspend fun saveCallResult(response: ExchangeResource) {
                exchangeDao.insertExchange(response.data.exchangeListModels.map { it.exchangeConvert() })
            }

            override fun loadFromDb(): LiveData<Exchange> {
                return exchangeDao.getExchange(uuid)
            }

            override suspend fun createCall(): Response<ExchangeResource> {
                return apiService.getExchangeList()
            }
        }.asLiveData()

}