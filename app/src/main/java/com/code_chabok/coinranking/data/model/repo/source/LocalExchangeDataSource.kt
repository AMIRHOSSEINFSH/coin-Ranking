package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.ExchangeDao


class LocalExchangeDataSource constructor( val exchangeDao: ExchangeDao) :
    ExchangeLocalDataSource {

    override suspend fun insertList(list: List<Exchange>) = exchangeDao.insertExchange(list)

}