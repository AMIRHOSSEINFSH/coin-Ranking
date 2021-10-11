package com.code_chabok.coinranking.feature.home

import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinView
import com.code_chabok.coinranking.common.CoinViewModel
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
    private val getCoin: getCoinDetail, private val updateBookmark: updateBookmark,
    private val getSortedList: getSortedList
) : CoinViewModel() {

    private var _priceSortLiveData = MutableLiveData<List<CoinListModel>>()
    var priceSortLiveData: LiveData<List<CoinListModel>> = _priceSortLiveData

    private val _timeSortLiveData = MutableLiveData<List<CoinListModel>>()
    val timeSortLiveData: LiveData<List<CoinListModel>> get() = _timeSortLiveData

    private val _marketCapLiveData = MutableLiveData<List<CoinListModel>>()
    val marketCapLiveData: LiveData<List<CoinListModel>> get() = _marketCapLiveData

    private val _coinListLiveData = MediatorLiveData<List<CoinListModel>>()
    val coinListLiveData: LiveData<List<CoinListModel>> get() = _coinListLiveData

    init {
        _coinListLiveData.addSource(priceSortLiveData) { value ->
            _coinListLiveData.value = value
        }
        _coinListLiveData.addSource(timeSortLiveData) { value ->
            _coinListLiveData.value = value
        }
        _coinListLiveData.addSource(marketCapLiveData) { value ->
            _coinListLiveData.value = value
        }
    }

    sealed class SortType(val body: String) {
        class Time(endingRequestBody: String) : SortType(endingRequestBody)
        class Price(Order: String) : SortType(Order)
        class MarketCap(Order: String) : SortType(Order)
    }

    fun onChangeSort(type: SortType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                is SortType.Time -> {
                    _timeSortLiveData.postValue(getSortedList(type)!!)
                }
                is SortType.Price -> {
                    _priceSortLiveData.postValue(getSortedList(type)!!)
                }
                is SortType.MarketCap -> {
                    _marketCapLiveData.postValue(getSortedList(type)!!)
                }
            }
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
        getListOfCoins()
    }

}