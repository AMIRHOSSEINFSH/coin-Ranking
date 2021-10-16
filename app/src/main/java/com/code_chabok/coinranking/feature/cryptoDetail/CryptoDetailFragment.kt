package com.code_chabok.coinranking.feature.cryptoDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterCrypto
import com.code_chabok.coinranking.components.zoomOutTransformer
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.databinding.FragmentCryptoDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoDetailFragment : CoinFragment() {


    private lateinit var binding: FragmentCryptoDetailBinding
    private val vieModel: CryptoDetailViewModel by /*navGraphViewModels<CryptoDetailViewModel>(R.id.nav_home)*/activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //setShimmerIndicator(true)
        // Inflate the layout for this fragment
        binding = FragmentCryptoDetailBinding.inflate(inflater, container, false)
        //setShimmerIndicator(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.mi_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShimmerIndicator(true,DetailPage = true)
        binding.parent.isVisible = false
        val uuid = arguments?.getString("uuid")
        vieModel.setUuid(uuid!!)

        binding.cryptoBookmarkIv.setOnClickListener {
            if (it.tag == R.drawable.ic_bookmarks_fill) {
                binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_empty
                binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_empty)
                vieModel.updateNewBookmark(uuid,false)
            } else if (it.tag == R.drawable.ic_bookmarks_empty) {
                binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_fill
                binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_fill)
                vieModel.updateNewBookmark(uuid,true)
            }
        }

        vieModel.coinDetail.observe(viewLifecycleOwner) { resource ->

            checkResponseForView(resource, onSuccess = {
                binding.parent.isVisible = true
                val coin = resource.data as CoinDetail
                binding.model = coin
                setShimmerIndicator(false)
                binding.cryptoBookmarkIv.also {
                    if (coin.isBookmark) {
                        it.tag = R.drawable.ic_bookmarks_fill
                        it.setImageResource(R.drawable.ic_bookmarks_fill)
                    } else {
                        it.tag = R.drawable.ic_bookmarks_empty
                        it.setImageResource(R.drawable.ic_bookmarks_empty)
                    }
                }

            },
                onError = {
                    binding.parent.isVisible = true

                    binding.model = resource.data as CoinDetail
                    setShimmerIndicator(false)
                }
            )
        }


        binding.viewPager.adapter = FragmentAdapterCrypto(this)
        binding.viewPager.setPageTransformer(zoomOutTransformer())
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
        setShimmerIndicator(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("TAG", "onDestroyViewsfnvdsnuo: ")
        vieModel.onFinish()
    }


}
