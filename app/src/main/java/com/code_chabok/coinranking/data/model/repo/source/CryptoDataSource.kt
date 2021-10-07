package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.Crypto

interface CryptoDataSource {

    fun getAllCryptos():List<Crypto>
    fun insertList(list: List<Crypto>)
}