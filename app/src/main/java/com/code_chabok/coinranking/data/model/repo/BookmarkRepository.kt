package com.code_chabok.coinranking.data.model.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.services.http.ApiService
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao
) {

     fun getListOfBookmarks(): LiveData<List<CoinAndBookmark>> {
       return coinDao.getCoinsAndBookMarks()
    }
}