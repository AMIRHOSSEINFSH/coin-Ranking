package com.code_chabok.coinranking.feature.bookMarks

import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.domain.getCoinDetail
import com.code_chabok.coinranking.domain.getListOfBookmarks
import com.code_chabok.coinranking.domain.updateBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookMarksViewModel @Inject constructor(
    private val getCoin: getCoinDetail,
    private val updateBookmark: updateBookmark,
    private val getListOfBookmarks: getListOfBookmarks

) : CoinViewModel() {

    private val _coinDetailObserver = MutableLiveData<CoinDetail>()
    val coinDetailObserver get() = _coinDetailObserver

    fun getSpcificCoinDetail(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCoin(uuid).data
            withContext(Dispatchers.Main) {
                _coinDetailObserver.value = res!!
            }
        }
    }

    fun updateNewBookmark(uuid: String, isBookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmark(uuid, isBookmark)
        }
    }

    val listCoin = refreshing.switchMap {
        getListOfBookmarks()
    }

}