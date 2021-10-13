package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.services.http.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService,private val coinDao: CoinDao) {


    suspend fun search(query: String): List<CoinListModel>{
        val response = asApiResponse { apiService.getSearchResult(query) }
        return when(response){
            is ApiSuccessResponse ->{
                val result = response.body.data.coins
                val prevList = coinDao.getBookmarksUuid()
                val resultList = ArrayList<CoinListModel>()
                result.forEach { coinListModel ->
                    if (prevList.contains(coinListModel.uuid)){
                        coinListModel.isBookmarked = true
                    }
                    resultList.add(coinListModel)
                }
                resultList
            }
            is ApiEmptyResponse ->{
                emptyList<CoinListModel>()
            }
            is ApiErrorResponse ->{
                emptyList<CoinListModel>()
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