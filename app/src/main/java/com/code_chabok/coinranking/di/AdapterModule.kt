package com.code_chabok.coinranking.di

import com.code_chabok.coinranking.feature.bookMarks.BookMarkAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun getBookmarkAdapter(): BookMarkAdapter {
        return BookMarkAdapter()
    }
}