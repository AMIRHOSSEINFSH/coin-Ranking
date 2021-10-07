package com.code_chabok.coinranking.data.model.repo

import com.code_chabok.coinranking.data.model.Crypto

interface CryptoRepository {

    fun fetchCrypto():List<Crypto>

}