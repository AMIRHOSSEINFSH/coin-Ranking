package com.code_chabok.coinranking.data.db

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

 /*   @Provides
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
        bookmarkDao: BookmarkDao,
        apiService: ApiService
    ): CoinListRepository {
        return CoinListRepository(
            apiService, coinDao,bookmarkDao
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
        apiService: ApiService,
        exchangeDao: ExchangeDao
    ): ExchangeRepository {
        return ExchangeRepositoryImp(apiService,exchangeDao)
    }
*/
}