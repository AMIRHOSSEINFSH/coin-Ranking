package com.code_chabok.coinranking.feature.home

import android.util.Log
import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinView
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.repo.CryptoRepository
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

    sealed class SortType(val body: String) {
        class Time(endingRequestBody: String) : SortType(endingRequestBody)
        class Price(Order: String) : SortType(Order)
        class MarketCap(Order: String) : SortType(Order)
    }


    fun onChangeSort(type: SortType) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSortedList(type)
            if (result.isEmpty()){
                errorLiveData.postValue("Sort is Not Compatible")
            }
            /*if (result.isEmpty()) {
                errorLiveData.postValue(true)
            } else {
                withContext(Dispatchers.Main) {
                    _listLiveData.value = result
                }
            }*/
        }

    }

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
                _coinDetailObserver.value = res!!
            }
        }
    }


    var listCoins = refreshing.switchMap {
        Log.i("TAG", "refreshing: ")
        getListOfCoins()
    }

}