package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.code_chabok.coinranking.common.NetworkBoundResource
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeResource
import com.code_chabok.coinranking.data.model.repo.source.ExchangeLocalDataSource
import com.code_chabok.coinranking.data.model.repo.source.ExchangeRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ExchangeRepositoryImp @Inject
constructor(
    val localDataSource: ExchangeLocalDataSource,
    val remoteDataSource: ExchangeRemoteDataSource
) : ExchangeRepository {


    override fun getExchangeList(): LiveData<Resource<List<ExchangeListModel>>> =
        object : NetworkBoundResource<List<ExchangeListModel>, ExchangeResource>() {
            override suspend fun saveCallResult(response: ExchangeResource) {

                val exchangeListModels = response.data.exchangeListModels.map {
                    it.exchangeConvert()
                }
                localDataSource.insertExchange(exchangeListModels)

            }

            override fun loadFromDb(): LiveData<List<ExchangeListModel>> {

                return localDataSource.getExchanges().map { list ->
                    list.map { exchange ->
                        exchange.convertExchangeListModel()
                    }
                }
            }

            override suspend fun createCall(): Response<ExchangeResource> =
                remoteDataSource.getExchangeList()


        }.asLiveData()

//    override fun getExchange(id: String): LiveData<Resource<ExchangeListModel>> {
//        TODO("Not yet implemented")
//    }


}