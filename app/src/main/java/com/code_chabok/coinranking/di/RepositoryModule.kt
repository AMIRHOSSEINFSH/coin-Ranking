package com.code_chabok.coinranking.data.db

import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
import com.code_chabok.coinranking.data.model.repo.*
import com.code_chabok.coinranking.data.model.repo.source.LocalCryptoDataSource
import com.code_chabok.coinranking.data.model.repo.source.LocalExchangeDataSource
import com.code_chabok.coinranking.data.model.repo.source.RemoteCryptoDataSource
import com.code_chabok.coinranking.data.model.repo.source.RemoteExchangeDataSource
import com.code_chabok.coinranking.services.http.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(
        //todo
        apiService: ApiService
    ): CryptoRepository {
        return CryptoRepositoryImp(
            LocalCryptoDataSource(),
            RemoteCryptoDataSource(apiService)
        )

    }

    @Provides
    @Singleton
    fun provideCoinListRepo(
        //todo
        coinDao: CoinDao,
        apiService: ApiService
    ): CoinListRepository {
        return CoinListRepository(
            apiService,coinDao
        )

    }

    @Provides
    @Singleton
    fun provideExchangeRepoImp(
        //todo
        apiService: ApiService
    ): ExchangeRepository {
        return ExchangeRepositoryImp(
            LocalExchangeDataSource(),
            RemoteExchangeDataSource(apiService)
        )
    }

}