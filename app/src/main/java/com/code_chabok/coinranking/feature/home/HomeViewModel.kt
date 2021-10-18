package com.code_chabok.coinranking.feature.home

import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.domain.getCoinDetail
import com.code_chabok.coinranking.domain.getListOfCoins
import com.code_chabok.coinranking.domain.getSortedList
import com.code_chabok.coinranking.domain.updateBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListOfCoins: getListOfCoins,
    private val getCoin: getCoinDetail,
    private val updateBookmark: updateBookmark,
    private val getSortedList: getSortedList
) : CoinViewModel() {


    //todo Sort Params
    private var limit = 100
    private var timePeriod = "24h"
    private var orderBy = "marketCap"
    private var orderDirection = "desc"

    //todo how Changing Sort Params!!
    fun setSortArguments(
        limit: Int = 100,
        timePeriod: String = "24h",
        orderBy: String = "marketCap",
        orderDirection: String = "desc"
    ) {
        this.limit = limit
        this.timePeriod = timePeriod
        this.orderBy = orderBy
        this.orderDirection = orderDirection

    }

    //todo initial List (refreshing)
    var listCoins = refreshing.switchMap { isLock ->
        //todo isLock is for times that we have conflicts with sorting so we should lock this liveData & Unlock it in proper times
        if (isLock) {
            getListOfCoins()
        } else {
            liveData<Resource<List<CoinListModel>>> { emit(Resource.Error<List<CoinListModel>>("refreshing is Lock!!")) }
        }
    }


    //todo use for sorting
    private val _sortListLiveData = MutableLiveData<Resource<List<CoinAndBookmark>>>()
    val sortListLiveData: LiveData<Resource<List<CoinAndBookmark>>> get() = _sortListLiveData

    fun onChangeSort() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSortedList(limit,orderBy,timePeriod, orderDirection)
            when (result) {
                is Resource.Success->{
                    _sortListLiveData.postValue(result)
                }
                is Resource.Error -> {
                    errorLiveData.postValue(result.message)
                    _sortListLiveData.postValue(result)
                }
            }
        }

    }

    //todo use for times that we onLongClick on RecyclerView Items
    fun updateNewBookmark(uuid: String, isBookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmark(uuid, isBookmark)
        }
    }

    private val _coinDetailObserver = MutableLiveData<CoinDetail>()
    val coinDetailObserver get() = _coinDetailObserver

    fun getSpcificCoinDetail(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCoin(uuid).data
            withContext(Dispatchers.Main) {
                res?.also {
                    _coinDetailObserver.value = it
                }
            }
        }
    }



}