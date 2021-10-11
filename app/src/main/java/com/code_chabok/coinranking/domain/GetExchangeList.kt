package com.code_chabok.coinranking.domain

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.ExchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.data.model.repo.ExchangeRepositoryImp
import com.cupcake.cupcakeretrofit.core.Resource
import com.cupcake.cupcakeretrofit.data.model.CoinEntity
import com.cupcake.cupcakeretrofit.data.model.User
import com.cupcake.cupcakeretrofit.data.model.response.Coin
import com.cupcake.cupcakeretrofit.data.repo.CoinRankingRepository
import com.cupcake.cupcakeretrofit.data.repo.UsersRepository
import javax.inject.Inject

class GetExchangeList @Inject constructor(
    private val exchangeRepositoryImp: ExchangeRepositoryImp
) {

    operator fun invoke(): LiveData<Resource<List<ExchangeListModel>>> {
        return exchangeRepositoryImp.GetExchangeList()
    }

}