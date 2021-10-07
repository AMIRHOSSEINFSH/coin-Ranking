package com.code_chabok.coinranking.data.model.repo

import com.code_chabok.coinranking.data.model.Exchange
import com.code_chabok.coinranking.data.model.repo.source.LocalCryptoDataSource
import com.code_chabok.coinranking.data.model.repo.source.LocalExchangeDataSource
import com.code_chabok.coinranking.data.model.repo.source.RemoteCryptoDataSource
import com.code_chabok.coinranking.data.model.repo.source.RemoteExchangeDataSource

class ExchangeRepositoryImp
    constructor(
        private val localCryptoDataSource: LocalExchangeDataSource,
        private val remoteCryptoDataSource: RemoteExchangeDataSource
): ExchangeRepository {

    override fun fetch(): List<Exchange> {
        val list = remoteCryptoDataSource.getAllCryptos()
        localCryptoDataSource.insertList(list)
        return localCryptoDataSource.getAllCryptos()
    }
}