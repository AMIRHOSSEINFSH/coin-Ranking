package com.code_chabok.coinranking.data.repo.source

import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.services.http.ApiService

class RemoteCryptoDataSource constructor(apiService: ApiService): CryptoDataSource {


    override fun getAllCryptos():List<Crypto> {
        return emptyList()
    }

    override fun insertList(list: List<Crypto>) {

    }

}