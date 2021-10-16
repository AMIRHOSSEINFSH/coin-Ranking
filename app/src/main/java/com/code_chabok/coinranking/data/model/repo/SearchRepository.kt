package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.Coin
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.localModel.ExchangeDao
import com.code_chabok.coinranking.data.model.dataClass.searchModel.SearchResult
import com.code_chabok.coinranking.services.http.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val exchangeDao: ExchangeDao
) {

    private fun getBookmarks(): List<String> {
        return coinDao.getBookmarksUuid()
    }

    suspend fun search(query: String): SearchResult {
        val response = asApiResponse { apiService.getSearchResult(query) }
        return when (response) {
            is ApiSuccessResponse -> {
                val coinresult = response.body.data.coins
                val exchangeResult = response.body.data.exchanges
                val coinDb: List<Coin> = coinresult.map {
                    it.convertToCoin()
                }
                val exchangeDb: List<Exchange> = exchangeResult.map {
                    it.exchangeConvert()
                }
                coinDao.insetCoins(coinDb)
                exchangeDao.insertExchange(exchangeDb)

                val prevList = getBookmarks()
                val resultList = ArrayList<CoinListModel>()
                coinresult.forEach { coinListModel ->
                    if (prevList.contains(coinListModel.uuid)) {
                        coinListModel.isBookmarked = true
                    }
                    resultList.add(coinListModel)
                }
                SearchResult(coinresult, exchangeResult)
            }
            is ApiEmptyResponse -> {
                SearchResult(null, null)
            }
            is ApiErrorResponse -> {
                SearchResult(null, null)
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


}