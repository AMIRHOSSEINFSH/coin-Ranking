package com.code_chabok.coinranking.feature.cryptoDetail

import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.domain.getCryptoDetail
import com.code_chabok.coinranking.domain.getListOfCoins
import com.code_chabok.coinranking.domain.updateBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val coinDetailUseCase: getCryptoDetail,
    private val updateBookmark: updateBookmark,
) : CoinViewModel() {

    private lateinit var uuid: String
    fun setUuid(uuid: String){
        this.uuid = uuid
    }
    fun getUuid() = uuid

    fun updateNewBookmark(uuid: String, isBookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmark(uuid, isBookmark)
        }
    }


    var coinDetail = refreshing.switchMap {isLock->
        if (isLock){
            coinDetailUseCase(uuid)
        }
        else{
            liveData { emit(Resource.Error<List<CoinDetail>>("refreshing is Lock!!")) }
        }
    }

}