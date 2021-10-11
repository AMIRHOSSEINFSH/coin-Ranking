package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource.CoinListResource
import com.code_chabok.coinranking.feature.home.HomeViewModel
import com.code_chabok.coinranking.services.http.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CoinListRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao
) {




    suspend fun getSortedList(type: HomeViewModel.SortType): List<CoinListModel> {

         return when(type){
            is HomeViewModel.SortType.Time->{
                Log.i("TAGAAAA", "getSortedList: ${type.body}")
                apiService.getListAs(timePeriod = type.body!!).body()?.data?.coinListModels!!
            }
            is HomeViewModel.SortType.Price->{
                val value = apiService.getListAs(kindOfOrder = type.body!!).body()?.data?.coinListModels!!
                value
            }
            is HomeViewModel.SortType.MarketCap->{
                apiService.getListAs(kindOfOrder = type.body!!).body()?.data?.coinListModels!!
            }
        }
    }

    suspend fun updateBookmark(uuid: String, isBookmark: Boolean): Int {
        return coinDao.updateBookmark(uuid, isBookmark)
    }

    suspend fun getDetailCoin(uuid: String): Resource<CoinDetail> {
        Log.i("TAGAAAAA", "getDetailCoin: ${uuid}")
        val resource = asApiResponse { apiService.getDetailedCoin(uuid.trim()) }
        return when (resource) {
            is ApiSuccessResponse -> {
                Resource.Success(resource.body.data.coin)
            }
            is ApiErrorResponse -> {
                Resource.Error(resource.errorMessage)
            }
            is ApiEmptyResponse -> {
                Resource.Error("Empty Body")
            }
        }

    }

    fun getListOfCoin(): LiveData<Resource<List<CoinListModel>>> =

        object : NetworkBoundResource<List<CoinListModel>, CoinListResource>() {
            override suspend fun saveCallResult(response: CoinListResource) {

                val list = ArrayList<Coin>()
                response.data.coinListModels.forEach {
                    list.add(it.convertToCoin())
                }
                coinDao.insetCoins(list)

            }

            override fun loadFromDb(): LiveData<List<CoinListModel>> {

                return Transformations.map(coinDao.getCoins()) { list ->
                    val coinList = ArrayList<CoinListModel>()
                    list.forEach {
                        coinList.add(it.convertToCoinList())
                    }
                    coinList
                }
            }

            override suspend fun createCall(): Response<CoinListResource> {
                return apiService.getCoinList()
            }
        }.asLiveData()

}