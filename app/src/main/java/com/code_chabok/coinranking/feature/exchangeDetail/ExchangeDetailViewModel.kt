package com.code_chabok.coinranking.feature.exchangeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.switchMap
import com.code_chabok.coinranking.common.CoinViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    //getExchangeDetailUseCase: GetExchangeDetailUseCase
) : CoinViewModel() {

    private var id:String = savedStateHandle.get<String>("id")
       ?: throw IllegalArgumentException("id must not be null")


    /*val exchangeResource = refreshing.switchMap {
        getExchangeDetailUseCase(id)
    }*/


}