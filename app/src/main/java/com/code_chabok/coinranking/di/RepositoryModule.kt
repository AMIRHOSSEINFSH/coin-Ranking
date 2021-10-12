package com.code_chabok.coinranking.data.db

import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.ExchangeDao
import com.code_chabok.coinranking.data.model.repo.*
import com.code_chabok.coinranking.data.model.repo.source.*
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
            apiService, coinDao
        )

    }

    @Provides
    @Singleton
    fun provideExchangeRepoLocalDataSource(
        exchangeDao: ExchangeDao
    ): ExchangeLocalDataSource {
        return LocalExchangeDataSourceImpl(exchangeDao)
    }

    @Provides
    @Singleton
    fun provideExchangeRepoRemoteDataSource(
        apiService: ApiService
    ): ExchangeRemoteDataSource {
        return RemoteExchangeDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideExchangeRepoImp(
        localDataSource: ExchangeLocalDataSource,
        remoteDataSource: ExchangeRemoteDataSource
    ): ExchangeRepository {
        return ExchangeRepositoryImp(localDataSource,remoteDataSource)
    }

}