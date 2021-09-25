package com.code_chabok.coinranking.feature.exchangeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterCrypto
import com.code_chabok.coinranking.common.FragmentAdapterExchange
import com.code_chabok.coinranking.databinding.FragmentExchangeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class ExchangeDetailFragment : CoinFragment() {

    private lateinit var binding : FragmentExchangeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentExchangeDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = FragmentAdapterExchange(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->
            when(position){
                0-> tab.text = "OverView"
                1-> tab.text = "Cryptocurrencies"
            }
        }.attach()
    }




}