package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.dataClass.LocalModel.Exchange

interface ExchangeLocalDataSource {

    suspend fun insertList(list: List<Exchange>)

}