package com.code_chabok.coinranking.feature.exchanges

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.databinding.FragmentExchangesBinding

class ExchangesFragment : CoinFragment() {

    private lateinit var binding: FragmentExchangesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExchangesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.setOnClickListener {
            findNavController().navigate(R.id.action_exchangesFragment_to_searchFragment2)
        }
    }

}