package com.code_chabok.coinranking.feature.home

import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinView
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.repo.CryptoRepository
import com.code_chabok.coinranking.domain.getCoinDetail
import com.code_chabok.coinranking.domain.getListOfCoins
import com.code_chabok.coinranking.domain.updateBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListOfCoins: getListOfCoins,
    private val getCoin: getCoinDetail
    ,private val updateBookmark: updateBookmark
) : CoinViewModel() {

    fun updateNewBookmark(uuid: String,isBookmark: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmark(uuid,isBookmark)
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