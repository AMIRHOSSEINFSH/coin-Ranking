package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.*
import com.code_chabok.coinranking.data.model.dataClass.searchModel.SearchResult
import com.code_chabok.coinranking.services.http.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val exchangeDao: ExchangeDao,
    private val bookmarkDao: BookmarkDao
) {

    suspend fun search(query: String): SearchResult {
        val response = asApiResponse { apiService.getSearchResult(query) }
        return when (response) {
            is ApiSuccessResponse -> {
                val coinList = response.body.data.searchCoins
                val exchangeList = response.body.data.searchExchanges
                coinList.map {searchModelCoin->
                    coinDao.updateSearchCoin(searchModelCoin.uuid,searchModelCoin.iconUrl,searchModelCoin.name)
                }
                exchangeList.map {searchModelExchange->
                    exchangeDao.updateSearchExchange(searchModelExchange.uuid,searchModelExchange.iconUrl,searchModelExchange.name)
                }
                SearchResult(coinDao.getSearchedCoins(query), exchangeDao.getSearchedExchanges(query))
            }
            is ApiEmptyResponse -> {
                SearchResult(null, null)
            }
            is ApiErrorResponse -> {
                SearchResult(null, null)
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