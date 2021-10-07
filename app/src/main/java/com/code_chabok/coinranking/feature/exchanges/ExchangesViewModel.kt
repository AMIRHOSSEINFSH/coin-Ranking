package com.code_chabok.coinranking.feature.exchanges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.code_chabok.coinranking.common.CoinView
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.Exchange
import com.code_chabok.coinranking.data.model.repo.ExchangeRepository
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(var repository: ExchangeRepository): CoinViewModel() {

    private var _exchangeListLiveData = MutableLiveData<List<Exchange>>()
    val exchangeListLiveData : LiveData<List<Exchange>>
        get() = _exchangeListLiveData

    init {
        viewModelScope.launch {
            delay(2000)
            _exchangeListLiveData.postValue(repository.fetch())
        }

    }

}