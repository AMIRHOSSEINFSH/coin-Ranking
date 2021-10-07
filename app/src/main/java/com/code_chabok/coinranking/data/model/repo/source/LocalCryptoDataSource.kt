package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.Crypto

class LocalCryptoDataSource: CryptoDataSource {

    override fun getAllCryptos():List<Crypto> {
         return arrayListOf<Crypto>(
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "1"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "2"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "3"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "4"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "5"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "6"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "7"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "8"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "9"
            )
        )
    }

    override fun insertList(list: List<Crypto>) {

    }
}