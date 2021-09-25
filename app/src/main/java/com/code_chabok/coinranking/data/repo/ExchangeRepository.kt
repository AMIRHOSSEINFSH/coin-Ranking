package com.code_chabok.coinranking.data.repo

import com.code_chabok.coinranking.data.model.Exchange

interface ExchangeRepository {

    fun fetch():List<Exchange>
}