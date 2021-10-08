package com.code_chabok.coinranking.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.SearchModel.SearchResource
import com.code_chabok.coinranking.domain.Search
import com.code_chabok.coinranking.domain.getCoinDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDomain: Search,
    private val getCoin: getCoinDetail
) : CoinViewModel() {

    private var searchJob: Job = Job()

    private val _resultSearchResource = MutableLiveData<Resource<SearchResource>>()
    val resultSearchResource: LiveData<Resource<SearchResource>> get() = _resultSearchResource

    fun search(query: String) {
        searchJob.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            val resource = searchDomain(query)
            withContext(Dispatchers.Main) {
                _resultSearchResource.value = resource
            }
        }
    }

    private val _coinDetailObserver = MutableLiveData<CoinDetail>()
    val coinDetailObserver get() = _coinDetailObserver

    fun getSpcificCoinDetail(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCoin(uuid).data
            withContext(Dispatchers.Main) {
                _coinDetailObserver.value = res!!
            }
        }
    }

}