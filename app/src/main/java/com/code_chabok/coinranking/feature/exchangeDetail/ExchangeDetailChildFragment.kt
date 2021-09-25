package com.code_chabok.coinranking.feature.exchangeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment


class ExchangeDetailChildFragment : CoinFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange_detail_child, container, false)
    }


}