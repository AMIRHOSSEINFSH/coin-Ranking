package com.code_chabok.coinranking.feature.cryptoDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.FragmentAdapterCrypto
import com.code_chabok.coinranking.components.zoomOutTransformer
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.databinding.FragmentCryptoDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoDetailFragment : CoinFragment() {

    private lateinit var refAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>
    private lateinit var binding: FragmentCryptoDetailBinding
    private var uuid: String? = null
    private val viewModel: CryptoDetailViewModel by activityViewModels()/*navGraphViewModels<CryptoDetailViewModel>(R.id.nav_home) {
        defaultViewModelProviderFactory
    }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCryptoDetailBinding.inflate(inflater, container, false)
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
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.mi_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onChangeViewsByShimmer(false)
        getData()
        arrayAdapterConfig()
        observe()

        binding.cryptoBookmarkIv.setOnClickListener {
            Log.i("TAG", "performBookmarks: ${it.tag}")
            if (it.tag == R.drawable.ic_bookmarks_fill) {
                binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_empty
                binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_empty)
                viewModel.updateNewBookmark(uuid!!, false)
            } else if (it.tag == R.drawable.ic_bookmarks_empty) {
                binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_fill
                binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_fill)
                viewModel.updateNewBookmark(uuid!!, true)
            }
        }


    }


    private fun getData() {
        uuid = arguments?.getString("uuid")
        viewModel.setUuid(uuid!!)
    }

    private fun arrayAdapterConfig() {
        refAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.ref)
        )
        timeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.timeArray)
        )
        binding.apply {
            avTimeFilterDropDown.setAdapter(timeAdapter)
            avRefFilterDropDown.setAdapter(refAdapter)
        }
    }

    private fun observe() {

        binding.apply {

            avTimeFilterDropDown.setOnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        viewModel.setSortArguments(timePeriod = "24h")
                    }
                    1 -> {
                        viewModel.setSortArguments(timePeriod = "7d")
                    }
                    2 -> {
                        viewModel.setSortArguments(timePeriod = "30d")
                    }
                    3 -> {
                        viewModel.setSortArguments(timePeriod = "1y")
                    }
                    4 -> {
                        viewModel.setSortArguments(timePeriod = "5y")
                    }

                }
                onChangeViewsByShimmer(false)
                viewModel.sortItemBy()
            }
            avRefFilterDropDown.setOnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        //todo USD -> Default
                        viewModel.setSortArguments(ref = "Qwsogvtv82FCd")
                    }
                    1 -> {
                        //todo BTC ->
                        viewModel.setSortArguments(ref = "Qwsogvtv82FCd")
                    }
                    2 -> {
                        // TODO: ETH
                        viewModel.setSortArguments(ref = "razxDUgYGNAdQ")
                    }

                    3 -> {
                        //todo HEX
                        viewModel.setSortArguments(ref = "9K7m6ufraZ6gh")
                    }

                    4 -> {
                        //todo BNB
                        viewModel.setSortArguments(ref = "WcwrkfNI4FUAe")
                    }


                }
                onChangeViewsByShimmer(false)
                viewModel.sortItemBy()
            }
        }

        viewModel.coinDetailOnSort.observe(viewLifecycleOwner) { it ->
            checkResponseForView(it, onSuccess = {
                onChangeViewsByShimmer(true)
                val data = it.data
                binding.model = data?.coin
                binding.cryptoBookmarkIv.also {
                    if (data?.bookmark != null) {
                        it.tag = R.drawable.ic_bookmarks_fill
                        it.setImageResource(R.drawable.ic_bookmarks_fill)
                    } else {
                        it.tag = R.drawable.ic_bookmarks_empty
                        it.setImageResource(R.drawable.ic_bookmarks_empty)
                    }
                }
            }, onError = {
                onChangeViewsByShimmer(true)
                val model = it.data as CoinAndBookmark
                binding.model = model.coin

                binding.cryptoBookmarkIv.also {
                    if (model.bookmark != null) {
                        it.tag = R.drawable.ic_bookmarks_fill
                        it.setImageResource(R.drawable.ic_bookmarks_fill)
                    } else {
                        it.tag = R.drawable.ic_bookmarks_empty
                        it.setImageResource(R.drawable.ic_bookmarks_empty)
                    }
                }
            })
        }
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
        //onChangeViewsByShimmer(false)
        //viewModel.refresh()
    }

}
