package com.code_chabok.coinranking.data.model.repo.source

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.ExchangeDao
import javax.inject.Inject


class LocalExchangeDataSourceImpl @Inject constructor(val exchangeDao: ExchangeDao) :
    ExchangeLocalDataSource {

    override suspend fun insertExchange(list: List<Exchange>) = exchangeDao.insertExchange(list)

    override fun getExchanges(): LiveData<List<Exchange>> = exchangeDao.getExchanges()


}