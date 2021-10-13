package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import androidx.lifecycle.*
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource.CoinListResource
import com.code_chabok.coinranking.feature.home.HomeViewModel
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response
import javax.inject.Inject

class CoinListRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao
) {

    suspend fun getSortedList(type: HomeViewModel.SortType): Resource<List<CoinListModel>> {

        var respone: ApiResponse<CoinListResource>
        var sortedLocalList: List<Coin>
        when (type) {
            is HomeViewModel.SortType.Time -> {
                respone = asApiResponse { apiService.getListAs(timePeriod = type.body) }
            }
            is HomeViewModel.SortType.Price -> {
                respone = asApiResponse { apiService.getListAs(kindOfOrder = type.body) }
            }
            is HomeViewModel.SortType.MarketCap -> {
                respone = asApiResponse { apiService.getListAs(kindOfOrder = type.body) }
            }
        }
        val serverList: List<CoinListModel>
        val serverCoinList = ArrayList<Coin>()
        return when (respone) {
            is ApiSuccessResponse -> {
                val bookmarkIds: List<String> = coinDao.getBookmarksUuid()
                serverList = respone.body.data.coinListModels
                serverList.forEach { coinListModel ->
                    if (bookmarkIds.contains(coinListModel.uuid)) {
                        coinListModel.isBookmarked = true
                    }
                    serverCoinList.add(coinListModel.convertToCoin())
                }
                //coinDao.deleteAll()
                coinDao.insetCoins(serverCoinList)
                when (type) {
                    is HomeViewModel.SortType.Time -> {
                        sortedLocalList = coinDao.getPriceOrdered(true)
                    }
                    is HomeViewModel.SortType.Price -> {
                        sortedLocalList = coinDao.getPriceOrdered(type.orderArrow!!)
                    }
                    is HomeViewModel.SortType.MarketCap -> {
                        sortedLocalList = coinDao.getMarketCapOrdered(type.orderArrow!!)
                    }
                }

                val list = ArrayList<CoinListModel>()
                sortedLocalList.forEach { coin ->
                    list.add(coin.convertToCoinList())
                }
                Resource.Success(list)

            }
            is ApiErrorResponse -> {
                serverList = emptyList()
                Resource.Error<List<CoinListModel>>("Error Found in request !!")
            }
            is ApiEmptyResponse -> {
                serverList = emptyList()
                Resource.Error<List<CoinListModel>>("Empty List Found !!")
            }
        }
        //return serverList
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

                val bookmarkIds: List<String> = coinDao.getBookmarksUuid()
                val list = ArrayList<Coin>()
                response.data.coinListModels.forEach { coinListModel ->
                    if (bookmarkIds.contains(coinListModel.uuid)) {
                        coinListModel.isBookmarked = true
                    }
                    list.add(coinListModel.convertToCoin())
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