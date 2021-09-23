package com.code_chabok.coinranking.data.db

import com.code_chabok.coinranking.data.repo.CryptoRepository
import com.code_chabok.coinranking.data.repo.CryptoRepositoryImp
import com.code_chabok.coinranking.data.repo.source.LocalCryptoDataSource
import com.code_chabok.coinranking.data.repo.source.RemoteCryptoDataSource
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

}