package com.code_chabok.coinranking.feature.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.databinding.FragmentSearchBinding


class SearchFragment : CoinFragment() {

    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.mi_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

}