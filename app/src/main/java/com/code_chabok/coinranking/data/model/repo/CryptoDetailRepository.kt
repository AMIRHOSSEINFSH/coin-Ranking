package com.code_chabok.coinranking.data.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.Bookmark
import com.code_chabok.coinranking.data.model.dataClass.localModel.BookmarkDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.serverModel.coinDetailResource.CoinDetailResource
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response
import java.lang.StringBuilder
import java.util.regex.Pattern
import javax.inject.Inject

class CryptoDetailRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val bookmarkDao: BookmarkDao
) {

    suspend fun updateCoin(uuid: String) {
        val result = asApiResponse { apiService.getDetailedCoin(uuid) }
        when (result) {
            is ApiSuccessResponse -> {
                coinDao.updateCoin(result.body.data.coin.convertToCoin())
            }
            is ApiErrorResponse -> {
            }
            is ApiEmptyResponse -> {
            }
        }

    }

    suspend fun getSortedItemBy(
        uuid: String,
        ref: String,
        time: String
    ): Resource<CoinAndBookmark> {
        val bookmarkList = bookmarkDao.getBookmarks().map {
            it.uuid
        }
        val apiRequest = asApiResponse { apiService.getDetailCoinAs(uuid, ref, time) }
        Log.i("svdjisvdi", "getSortedItemBy: ${apiRequest}")
        return when (apiRequest) {
            is ApiSuccessResponse -> {
                var finalCoin: CoinAndBookmark

                if (bookmarkList.contains(apiRequest.body.data.coin.uuid)) {
                    finalCoin = CoinAndBookmark(
                        apiRequest.body.data.coin.convertToCoin(),
                        Bookmark(apiRequest.body.data.coin.uuid)
                    )
                } else {
                    finalCoin = CoinAndBookmark(apiRequest.body.data.coin.convertToCoin(),
                        null)
                }
                coinDao.insertCoin(apiRequest.body.data.coin.apply {
                    description = getOptimizedText(description!!, name!!)
                }.convertToCoin())

                Resource.Success(finalCoin.apply {
                    coin.description = getOptimizedText(coin.description!!, coin.name!!)
                })
            }
            is ApiErrorResponse -> {
                val localCoin = coinDao.getCoin(uuid)
                Log.i("bffdo", "getSortedItemBy: ${localCoin}")
                Resource.Error("you should Check your Connection before doing Sort!!", localCoin)
            }
            is ApiEmptyResponse -> {
                val localCoin = coinDao.getCoin(uuid)
                Log.i("bffdo", "getSortedItemBy: ${localCoin}")
                Resource.Error("Empty Response", localCoin)
            }
        }

    }

    private fun getOptimizedText(description: String, name: String): String {
        val pattern1 = Pattern.compile("<p>(.*?)</p>", Pattern.DOTALL)
        val pattern2 = Pattern.compile("<h3>(.*?)</h3>", Pattern.DOTALL)
        val matcher1 = pattern1.matcher(description)
        val matcher2 = pattern2.matcher(description)
        val descList = ArrayList<String>()
        val topicList = ArrayList<String>()
        topicList.add("What is $name")
        while (matcher1.find()) {
            val case = matcher1.group().replace("\n", "")
                .replace("<p>", "").replace("</p>", "")
            descList.add(case)
        }
        while (matcher2.find()) {
            val case = matcher2.group()
                .replace("<h3>", "").replace("</h3>", "")
            topicList.add(case)
        }
        val result = StringBuilder()
        for (i in topicList.indices) {
            result.append(topicList[i] + "\n" + descList[i] + "\n")
        }
        return result.toString()
    }

    fun getCoinDetailModel(uuid: String): LiveData<Resource<CoinAndBookmark>> =
        object : NetworkBoundResource<CoinAndBookmark, CoinDetailResource>() {
            override suspend fun saveCallResult(response: CoinDetailResource) {
                val detail = response.data.coin
                detail.description = getOptimizedText(detail.description!!, detail.name!!)
                val coin = detail.convertToCoin()
                coinDao.updateCoin(coin)
            }

            override fun loadFromDb(): LiveData<CoinAndBookmark> {
                return coinDao.getDetailedCoin(uuid)
            }

            override suspend fun createCall(): Response<CoinDetailResource> {
                return apiService.getDetailedCoin(uuid)
            }

        }.asLiveData()


}