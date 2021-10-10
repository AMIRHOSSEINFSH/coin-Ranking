package com.code_chabok.coinranking.data.model.repo

import com.code_chabok.coinranking.common.*
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.LocalModel.CoinDao
import com.code_chabok.coinranking.data.model.dataClass.SearchModel.SearchResource
import com.code_chabok.coinranking.services.http.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService,private val coinDao: CoinDao) {


    suspend fun search(query: String): Resource<SearchResource> {
        val response = asApiResponse { apiService.getSearchResult(query) }
        return when(response){
            is ApiSuccessResponse ->{
                Resource.Success(response.body)
            }
            is ApiEmptyResponse ->{
                Resource.Error("Empty Body")
            }
            is ApiErrorResponse ->{
                Resource.Error("Request Failed!")
            }
        }
    }
}