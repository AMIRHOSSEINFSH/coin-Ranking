package com.code_chabok.coinranking.data.repo.source

import com.code_chabok.coinranking.data.model.Exchange
import com.code_chabok.coinranking.services.http.ApiService

class RemoteExchangeDataSource(apiService: ApiService): ExchangeDataSource {

    override fun getAllCryptos(): List<Exchange> {
        return emptyList()
    }

    override fun insertList(list: List<Exchange>) {

    }
}