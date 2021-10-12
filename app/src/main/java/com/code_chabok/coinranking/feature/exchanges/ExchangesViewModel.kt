package com.code_chabok.coinranking.feature.exchanges

import androidx.lifecycle.switchMap
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.domain.GetExchangeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
     val GetExchangeListUseCase: GetExchangeListUseCase
) : CoinViewModel() {


 val exchangeResource = refreshing.switchMap {
     GetExchangeListUseCase()
 }




}