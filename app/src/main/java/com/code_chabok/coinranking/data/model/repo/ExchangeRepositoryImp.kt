package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.NetworkBoundResource
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.localModel.ExchangeDao
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeResource
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response
import javax.inject.Inject

class ExchangeRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val exchangeDao: ExchangeDao
)  {


     fun getExchangeList(): LiveData<Resource<List<Exchange>>> =
        object : NetworkBoundResource<List<Exchange>, ExchangeResource>() {
            override suspend fun saveCallResult(response: ExchangeResource) {

                val exchangeListModels = response.data.exchangeListModels.map {
                    it.exchangeConvert()
                }
                exchangeDao.insertExchange(exchangeListModels)

            }

            override fun loadFromDb(): LiveData<List<Exchange>> {
                return exchangeDao.getExchanges()
            }

            override suspend fun createCall(): Response<ExchangeResource> =
                apiService.getExchangeList()


        }.asLiveData()

}