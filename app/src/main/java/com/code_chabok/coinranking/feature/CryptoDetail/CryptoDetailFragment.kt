package com.code_chabok.coinranking.feature.CryptoDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterCrypto
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.databinding.FragmentCryptoDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class CryptoDetailFragment : CoinFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding : FragmentCryptoDetailBinding? = null
    private val binding : FragmentCryptoDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //setShimmerIndicator(true)
        // Inflate the layout for this fragment
        _binding = FragmentCryptoDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val crypto = CryptoDetailFragmentArgs.fromBundle(requireArguments()).crypto
        val crypto = arguments?.getParcelable<Crypto>("item")
        binding.model = crypto

        binding.viewPager.adapter = FragmentAdapterCrypto(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->
            when(position){
                0-> tab.text = "OverView"
                1-> tab.text = "Exchanges"
            }
        }.attach()


    }


    override fun onStop() {
        super.onStop()
       // setShimmerIndicator(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    }
