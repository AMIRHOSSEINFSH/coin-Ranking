package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.Exchange

interface ExchangeDataSource {

    fun getAllCryptos():List<Exchange>
    fun insertList(list: List<Exchange>)

}