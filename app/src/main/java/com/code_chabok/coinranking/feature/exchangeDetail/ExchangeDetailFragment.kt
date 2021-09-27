package com.code_chabok.coinranking.feature.exchangeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterCrypto
import com.code_chabok.coinranking.common.FragmentAdapterExchange
import com.code_chabok.coinranking.databinding.FragmentExchangeDetailBinding
import com.code_chabok.coinranking.feature.CryptoDetail.CryptoDetailChildFragment
import com.code_chabok.coinranking.feature.exchanges.ExchangesFragment
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
        binding.pager.adapter = TabAdapter(this)
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        TabLayoutMediator(binding.tab, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Cryptocurrencies"
            }
        }.attach()
    }


    class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CryptoDetailChildFragment()
                1 -> ExchangesFragment()
                else -> CryptoDetailChildFragment()
            }

        }


    }


}