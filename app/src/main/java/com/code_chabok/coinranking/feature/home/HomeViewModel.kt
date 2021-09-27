package com.code_chabok.coinranking.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.code_chabok.coinranking.common.CoinView
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.repo.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(var repository: CryptoRepository): CoinView.CoinViewModel() {

    private var _cryptoListLiveData = MutableLiveData<List<Crypto>>()
    val cryptoListLiveData : LiveData<List<Crypto>>
        get() = _cryptoListLiveData

    init {
        viewModelScope.launch {
            delay(2000)
            _cryptoListLiveData.postValue(repository.fetchCrypto())
        }

    }

}