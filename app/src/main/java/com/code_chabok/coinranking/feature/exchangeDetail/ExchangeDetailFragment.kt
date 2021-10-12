package com.code_chabok.coinranking.feature.exchangeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterExchange
import com.code_chabok.coinranking.components.zoomOutTransformer
import com.code_chabok.coinranking.databinding.FragmentExchangeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class ExchangeDetailFragment : CoinFragment() {

    private lateinit var binding: FragmentExchangeDetailBinding
//    lateinit var viewModel: ExchangeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        viewModel = ViewModelProvider(requireActivity()).get(ExchangeDetailViewModel::class.java)
        binding = FragmentExchangeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = FragmentAdapterExchange(this)
        binding.pager.setPageTransformer(zoomOutTransformer())
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


}