package com.code_chabok.coinranking.feature.bookMarks

import android.app.Application
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.*
import com.code_chabok.coinranking.common.CoinView
import com.code_chabok.coinranking.common.CoinViewModel
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.repo.CryptoRepository
import com.code_chabok.coinranking.domain.getCoinDetail
import com.code_chabok.coinranking.domain.getListOfBookmarks
import com.code_chabok.coinranking.domain.updateBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

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