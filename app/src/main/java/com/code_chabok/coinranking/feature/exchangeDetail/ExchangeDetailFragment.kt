package com.code_chabok.coinranking.feature.exchangeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterExchange
import com.code_chabok.coinranking.components.zoomOutTransformer
import com.code_chabok.coinranking.databinding.FragmentExchangeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class ExchangeDetailFragment : CoinFragment() {

    private lateinit var binding: FragmentExchangeDetailBinding
    private val viewModel: ExchangeDetailViewModel by activityViewModels()
    private var uuid: String? = null

    private fun getData() {
        uuid = arguments?.getString("uuid")
        viewModel.setUuid(uuid!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExchangeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = FragmentAdapterExchange(this)
        binding.viewPager.setPageTransformer(zoomOutTransformer())
        setup()
        getData()
        viewModel.exchangeItem.observe(viewLifecycleOwner){
            checkResponseForView(it,onSuccess = {
                onChangeViewsByShimmer(true)
                binding.model = it.data
            },onError = {
                binding.model = it.data
                onChangeViewsByShimmer(true)
            }

            )}
    }

    private fun setup() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "OverView"
                }
                1 -> {
                    tab.text = "Cryptocurrencies"
                    setHasOptionsMenu(false)
                }
            }
        }.attach()
    }


    override fun onStop() {
        super.onStop()
        onChangeViewsByShimmer(true)
    }

    private fun onChangeViewsByShimmer(mustShow: Boolean) {
        binding.apply {
            setShimmerIndicator(!mustShow, DetailPage = true)
            constParent.isVisible = mustShow
        }

    }

    override fun onStart() {
        super.onStart()
        onChangeViewsByShimmer(false)
    }


}