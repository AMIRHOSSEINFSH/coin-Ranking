package com.code_chabok.coinranking.feature.exchangeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.domain.getExchangeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailViewModel @Inject constructor(
    getExchange: getExchangeItem
) : CoinViewModel() {

    private lateinit var uuid: String

    fun setUuid(uuid: String){
        this.uuid = uuid
    }

    val exchangeItem = refreshing.switchMap {
        getExchange(uuid)
    }


}