package com.code_chabok.coinranking.domain

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.repo.ExchangeRepository
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class getExchangeItem @Inject constructor(val repo: ExchangeRepository){

     operator fun invoke(uuid: String): LiveData<Resource<Exchange>> = repo.getExchanges(uuid)

}