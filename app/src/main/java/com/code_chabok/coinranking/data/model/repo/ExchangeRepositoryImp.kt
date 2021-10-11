package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.code_chabok.coinranking.common.NetworkBoundResource
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import com.code_chabok.coinranking.data.model.repo.source.LocalExchangeDataSource
import com.code_chabok.coinranking.data.model.repo.source.RemoteExchangeDataSource
import retrofit2.Response

class ExchangeRepositoryImp
    constructor(
        private val localCryptoDataSource: LocalExchangeDataSource,
        private val remoteCryptoDataSource: RemoteExchangeDataSource
) {

    /*override fun fetch(): List<Exchange> {
        val list = remoteCryptoDataSource.getAllExchange()
        localCryptoDataSource.insertList(list)
        return localCryptoDataSource.getAllExchange()
    }*/


    fun getExchangeList () : LiveData<Resource<List<ExchangeListModel>>> =
        object : NetworkBoundResource<List<ExchangeListModel>, ExchangeResource>(){
            override suspend fun saveCallResult(response: ExchangeResource) {

                val list = ArrayList<Exchange>()
                response.data.exchangeListModels.forEach {
                    list.add(it.exchangeConvert())
                }
                localCryptoDataSource.exchangeDao.insertExchange(list)

            }

            override fun loadFromDb(): LiveData<List<ExchangeListModel>> {

                return Transformations.map(localCryptoDataSource.exchangeDao.getExchange()) { list ->
                    val exchangeList = ArrayList<ExchangeListModel>()
                    list.forEach {
                        exchangeList.add(it.convertExchangeListModel())
                    }
                    exchangeList
                }
            }

            override suspend fun createCall(): Response<ExchangeResource> {
                return remoteCryptoDataSource.apiService.getExchangeList()

            }

        }.asLiveData()



}