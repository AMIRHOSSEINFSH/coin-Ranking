package com.code_chabok.coinranking.feature.CryptoDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
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


    private var _binding: FragmentCryptoDetailBinding? = null
    private val binding: FragmentCryptoDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //setShimmerIndicator(true)
        // Inflate the layout for this fragment
        _binding = FragmentCryptoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.findItem(R.id.mi_search).isVisible = false

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val crypto = CryptoDetailFragmentArgs.fromBundle(requireArguments()).crypto
        val crypto = arguments?.getParcelable<Crypto>("item")
        binding.model = crypto

        val fragment = parentFragmentManager.findFragmentById(R.id.homeFragment)
        for (entry in 0 until parentFragmentManager.getBackStackEntryCount()) {
            //if (parentFragmentManager.getBackStackEntryAt(entry).getId() == R.id.homeFragment){
            Log.i(
                "Tag",
                "Found fragment: " + parentFragmentManager.getBackStackEntryAt(entry).getId()
            )
            // }

        }



        binding.viewPager.adapter = FragmentAdapterCrypto(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "OverView"
                }
                1 -> {
                    tab.text = "Exchanges"
                    setHasOptionsMenu(false)
                }
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
