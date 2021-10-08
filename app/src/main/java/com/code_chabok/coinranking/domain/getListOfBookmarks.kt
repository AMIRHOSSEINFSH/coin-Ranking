package com.code_chabok.coinranking.domain

import com.code_chabok.coinranking.data.model.repo.BookmarkRepository
import javax.inject.Inject

class getListOfBookmarks @Inject constructor(private val repo: BookmarkRepository) {

     operator fun invoke() = repo.getListOfBookmarks()

}