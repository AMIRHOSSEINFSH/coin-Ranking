package com.code_chabok.coinranking.data.repo

import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.repo.source.LocalCryptoDataSource
import com.code_chabok.coinranking.data.repo.source.RemoteCryptoDataSource

class CryptoRepositoryImp
constructor(
    private val localCryptoDataSource: LocalCryptoDataSource,
    private val remoteCryptoDataSource: RemoteCryptoDataSource
) : CryptoRepository {


    override fun fetchCrypto(): List<Crypto> {
        val list = remoteCryptoDataSource.getAllCryptos()
        localCryptoDataSource.insertList(list)
        return localCryptoDataSource.getAllCryptos()
    }
}