package com.code_chabok.coinranking.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.SearchModel.SearchResource
import com.code_chabok.coinranking.domain.Search
import com.code_chabok.coinranking.domain.getCoinDetail
import com.code_chabok.coinranking.domain.updateBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDomain: Search,
    private val getCoin: getCoinDetail,
    private val updateBookmark: updateBookmark
) : CoinViewModel() {

    fun updateNewBookmark(uuid: String,isBookmark: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmark(uuid,isBookmark)
        }
    }

    private var searchJob: Job = Job()

    private val _resultSearchResource = MutableLiveData<List<CoinListModel>>()
    val resultSearchResource: LiveData<List<CoinListModel>> get() = _resultSearchResource

    fun search(query: String) {
        searchJob.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            val resource = searchDomain(query)
            _resultSearchResource.postValue(resource)
        }
    }

    private val _coinDetailObserver = MutableLiveData<CoinDetail>()
    val coinDetailObserver get() = _coinDetailObserver

    fun getSpcificCoinDetail(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCoin(uuid).data
            withContext(Dispatchers.Main) {
                //_coinDetailObserver.value = res!!
            }
        }
    }


}