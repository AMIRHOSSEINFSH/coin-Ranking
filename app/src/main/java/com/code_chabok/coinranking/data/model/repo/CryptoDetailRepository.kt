package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.code_chabok.coinranking.common.NetworkBoundResource
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.serverModel.coinDetailResource.CoinDetailResource
import com.code_chabok.coinranking.services.http.ApiService
import retrofit2.Response
import java.lang.StringBuilder
import java.util.regex.Pattern
import javax.inject.Inject

class CryptoDetailRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao
) {

    fun getOptimizedText(description: String,name: String): String {
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

    fun getCoinDetailModel(uuid: String): LiveData<Resource<CoinDetail>> =
        object : NetworkBoundResource<CoinDetail, CoinDetailResource>() {
            override suspend fun saveCallResult(response: CoinDetailResource) {
                val detail = response.data.coin
                detail.description = getOptimizedText(detail.description!!,detail.name)
                val coin = detail.convertToCoin()
                coinDao.getBookmarksUuid().forEach {
                    if (it == detail.uuid) {
                        coin.isBookmarked = true
                    }
                }
                coinDao.updateCoin(coin)
            }

            override fun loadFromDb(): LiveData<CoinDetail> {
                return Transformations.map(coinDao.getDetailedCoin(uuid)) { coin ->
                    coin.convertToCoinDetail()
                }
            }

            override suspend fun createCall(): Response<CoinDetailResource> {
                return apiService.getDetailedCoin(uuid)
            }

        }.asLiveData()


}