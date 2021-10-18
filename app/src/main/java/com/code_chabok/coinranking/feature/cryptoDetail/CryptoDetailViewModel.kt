package com.code_chabok.coinranking.feature.cryptoDetail

import android.util.Log
import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val updateBookmark: updateBookmark,
    private val getSorted: getSortedItem,
    private val onUpdate: updateCoin
) : CoinViewModel() {

    fun setSortArguments(
        limit: Int = 100,
        timePeriod: String = "24h",
        ref: String = "Qwsogvtv82FCd",
        orderDirection: String = "desc"
    ) {
        this.limit = limit
        this.timePeriod = timePeriod
        this.ref = ref
        this.orderDirection = orderDirection

    }

    private var limit = 100
    private var timePeriod = "24h"
    private var ref = "Qwsogvtv82FCd"
    private var orderDirection = "desc"

    private lateinit var uuid: String
    fun setUuid(uuid: String){
        this.uuid = uuid
        viewModelScope.launch(Dispatchers.IO) {
            onUpdate(uuid)
            sortItemBy()
        }
    }
    fun getUuid() = uuid

    fun updateNewBookmark(uuid: String, isBookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmark(uuid, isBookmark)
        }
    }

    private val _coinDetailOnSort = MutableLiveData<Resource<CoinAndBookmark>>()
    val coinDetailOnSort : LiveData<Resource<CoinAndBookmark>>  get() = _coinDetailOnSort

    fun sortItemBy(){
        viewModelScope.launch {
            val res = getSorted(uuid,timePeriod,ref)
            _coinDetailOnSort.postValue(res)

        }
    }



}