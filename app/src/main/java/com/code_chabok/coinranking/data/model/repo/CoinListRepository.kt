package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import androidx.lifecycle.*
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.Bookmark
import com.code_chabok.coinranking.data.model.dataClass.localModel.BookmarkDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.serverModel.coinListResource.CoinListResource
import com.code_chabok.coinranking.feature.home.HomeViewModel
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response
import javax.inject.Inject

class CoinListRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val bookmarkDao: BookmarkDao
) {

    suspend fun getSortedList(type: HomeViewModel.SortType): Resource<List<CoinAndBookmark>> {

        var respone: ApiResponse<CoinListResource>
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

        val localList = coinDao.getCoinsAndBookMarks()
        return when (respone) {
            is ApiSuccessResponse -> {
                val b = bookmarkDao.getBookmarks().map {
                    it.uuid
                }
                val li = respone.body.data.coinListModels.map {
                    if (b.contains(it.uuid)) CoinAndBookmark(it.convertToCoin(), Bookmark(it.uuid))
                    else CoinAndBookmark(it.convertToCoin(), null)
                }

                //todo use for insert into Local only
                val insertationList = ArrayList<Coin>()
                //todo use for take to View Layer
                val serverToLocalList = ArrayList<Coin>()
                //todo use for timeSort Only (we must get it from server because we can not sort time by Local Database)
                val timeList = ArrayList<CoinAndBookmark>()
                //todo convert List from Server to CoinEntity for Insert
                respone.body.data.coinListModels.forEach {
                    insertationList.add(it.convertToCoin())
                }

                val bookmarkList = bookmarkDao.getBookmarks().map { bookmark ->
                    bookmark.uuid
                }
                serverToLocalList.forEach { coin ->
                    var isBookmarked = false
                    if (bookmarkList.contains(coin.uuid)) {
                        isBookmarked = true
                    }
                    timeList.add(
                        CoinAndBookmark(
                            coin,
                            if (isBookmarked) Bookmark(coin.uuid) else null
                        )
                    )
                }
                respone.body.data.coinListModels.forEach {
                    serverToLocalList.add(it.convertToCoin())
                }
                coinDao.insetCoins(insertationList.toList())
                when (type) {
                    is HomeViewModel.SortType.Time -> {
                        Resource.Success(li)
                    }
                    is HomeViewModel.SortType.Price -> {
                        Resource.Success(coinDao.getPriceOrdered(type.orderArrow!!))
                    }
                    is HomeViewModel.SortType.MarketCap -> {
                        Resource.Success(coinDao.getMarketCapOrdered(type.orderArrow!!))
                    }
                }
            }
            //todo if Reach this, it means that we must take the catch only to view layer
            is ApiErrorResponse -> {
                Resource.Error<List<CoinAndBookmark>>("Error Found in request !!", localList.value)
            }
            is ApiEmptyResponse -> {
                Resource.Error<List<CoinAndBookmark>>("Empty List Found !!", localList.value)
            }
        }
    }

    suspend fun updateBookmark(uuid: String, isBookmark: Boolean) {
        if (isBookmark) {
            bookmarkDao.insert(Bookmark(uuid))
        } else {
            bookmarkDao.remove(Bookmark(uuid))
        }
    }

    suspend fun getDetailCoin(uuid: String): Resource<CoinDetail> {
        Log.i("TAGAAAAA", "getDetailCoin: ${uuid}")
        val resource = asApiResponse { apiService.getDetailedCoin(uuid.trim()) }
        return when (resource) {
            is ApiSuccessResponse -> {
                val res = resource.body.data.coin
                coinDao.insertCoin(res.convertToCoin())
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

    fun getListOfCoin(): LiveData<Resource<List<CoinAndBookmark>>> =

        object : NetworkBoundResource<List<CoinAndBookmark>, CoinListResource>() {
            override suspend fun saveCallResult(response: CoinListResource) {

                val list = ArrayList<Coin>()
                response.data.coinListModels.forEach {
                    list.add(it.convertToCoin())
                }
                coinDao.insetCoins(list)

            }

            override fun loadFromDb(): LiveData<List<CoinAndBookmark>> {
                return coinDao.getCoinsAndBookMarks()
            }

            override suspend fun createCall(): Response<CoinListResource> {
                return apiService.getCoinList()
            }
        }.asLiveData()

}