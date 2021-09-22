package com.code_chabok.coinranking.data.repo.source

import com.code_chabok.coinranking.data.model.Crypto

class RemoteCryptoDataSource: CryptoDataSource {


    override fun getAllCryptos():List<Crypto> {
        return emptyList()
    }

    override fun insertList(list: List<Crypto>) {

    }

}