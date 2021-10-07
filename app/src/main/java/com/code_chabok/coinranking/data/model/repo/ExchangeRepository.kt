package com.code_chabok.coinranking.data.model.repo

import com.code_chabok.coinranking.data.model.Exchange

interface ExchangeRepository {

    fun fetch():List<Exchange>
}