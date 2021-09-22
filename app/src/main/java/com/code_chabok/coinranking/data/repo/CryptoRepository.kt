package com.code_chabok.coinranking.data.repo

import com.code_chabok.coinranking.data.model.Crypto

interface CryptoRepository {

    fun fetchCrypto():List<Crypto>

}