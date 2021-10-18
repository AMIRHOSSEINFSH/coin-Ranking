package com.code_chabok.coinranking.feature.home

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseCoinAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.OnChangeSort
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark

import com.code_chabok.coinranking.databinding.FragmentHomeBinding
import com.code_chabok.coinranking.domain.getCoinDetail
import com.nabilmh.lottieswiperefreshlayout.LottieSwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class HomeFragment : CoinFragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding? get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BaseCoinAdapter
    private lateinit var swipeRefreshLayout: LottieSwipeRefreshLayout

    private lateinit var priceAdapter : ArrayAdapter<String>
    private lateinit var marketAdapter:ArrayAdapter<String>
    private lateinit var timeAdapter :ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rootView = binding?.root as CoordinatorLayout

        return binding!!.root
    }

    private var isDetail = false
    fun isInDetail(boolean: Boolean): HomeFragment {
        isDetail = boolean
        return this
    }

   private fun arrayAdapterConfig(){
       priceAdapter = ArrayAdapter(
           requireContext(),
           android.R.layout.simple_list_item_1,
           resources.getStringArray(R.array.priceArray)
       )
       marketAdapter = ArrayAdapter(
           requireContext(),
           android.R.layout.simple_list_item_1,
           resources.getStringArray(R.array.marketCapArray)
       )
       timeAdapter = ArrayAdapter(
           requireContext(),
           android.R.layout.simple_list_item_1,
           resources.getStringArray(R.array.timeArray)
       )
       binding?.apply {
           avPriceFilterDropDown.setAdapter(priceAdapter)
           avTimeFilterDropDown.setAdapter(timeAdapter)
           avMarketFilterDropDown.setAdapter(marketAdapter)
       }
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayAdapterConfig()
        setUpSwipeRefresh()
        observe()

        viewModel.sortListLiveData.observe(viewLifecycleOwner) {
            checkResponseForView(it, onSuccess = {
                val coinListModel: List<CoinAndBookmark> = it.data as List<CoinAndBookmark>
                onChangeViewsByShimmer(true)
                viewModel.refresh(false)
                adapter.submitList(coinListModel) {
                    binding?.rvHome?.smoothScrollToPosition(0)
                }
            }, onError = {
                onChangeViewsByShimmer(true)
                viewModel.refresh(false)
                adapter.submitList(it.data as MutableList<CoinAndBookmark>?) {
                    binding?.rvHome?.smoothScrollToPosition(0)
                }
            })
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            showSnackBar(errorMessage)
            onChangeViewsByShimmer(true)
            viewModel.refresh(false)
        }

        if (isDetail) {
        }
        adapter =
            BaseCoinAdapter(
                onUpdateClickListener = { uuid: String, isBookmark: Boolean, _: Int ->
                    viewModel.updateNewBookmark(uuid, isBookmark)
                },
                onItemLongClickListener = { coinListModel ->
                    viewModel.getSpcificCoinDetail(coinListModel.coin.uuid)
                    viewModel.coinDetailObserver
                },
                onChangeDir = { isDetail: Boolean, position: Int ->
                    val bundle = Bundle().apply {
                        putString("uuid", adapter.currentList[position].coin.uuid)
                    }
                    if (!isDetail)
                        findNavController().navigate(R.id.home_book_same, bundle)
                    else
                        findNavController().navigate(R.id.action_same_to_same, bundle)

                })

        adapter.setActivity(requireActivity())
        adapter.apply {
            setActivity(requireActivity())
            setIsDetail(isDetail)
            showBubble = savedInstanceState == null
        }
        binding?.rvHome?.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.listCoins.observe(viewLifecycleOwner, {
            checkResponseForView(it, onSuccess = {
                viewModel.refresh(false)
                Log.i("TAG", "refreshing: ")
                val coinListModel: List<CoinAndBookmark> = it.data as List<CoinAndBookmark>
                onChangeViewsByShimmer(true)
                adapter.submitList(coinListModel) {
                    binding?.rvHome?.smoothScrollToPosition(0)
                }

            }, onError = {
                viewModel.refresh(false)
                onChangeViewsByShimmer(true)
                adapter.submitList(it.data as MutableList<CoinAndBookmark>?) {
                    binding?.rvHome?.smoothScrollToPosition(0)
                }
            })
        })


        binding?.rvHome?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding!!.rvHome.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding?.rvHome?.adapter = adapter

    }

    private fun setUpSwipeRefresh() {
        swipeRefreshLayout = _binding!!.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            onChangeViewsByShimmer(false)
            viewModel.refresh(true)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observe() {

        binding?.apply {

            avMarketFilterDropDown.setOnItemClickListener { parent, view, position, id ->
                viewModel.refresh(false)
                onChangeViewsByShimmer(false)
                when (position) {
                    0 -> {
                        viewModel.setSortArguments(orderBy = "marketCap", orderDirection = "desc")
                    }
                    1 -> {
                        viewModel.setSortArguments(orderBy = "marketCap", orderDirection = "desc")
                    }
                }
                avMarketFilterDropDown.setText("MarketCap",false)
                avMarketFilterDropDown.setSelection(avMarketFilterDropDown.text.count())
                viewModel.refresh(false)
                onChangeViewsByShimmer(false)
                viewModel.onChangeSort()
            }
            avTimeFilterDropDown.setOnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        viewModel.setSortArguments(timePeriod = "3h")
                    }
                    1 -> {
                        viewModel.setSortArguments(timePeriod = "24h")
                    }
                    2 -> {
                        viewModel.setSortArguments(timePeriod = "7d")
                    }
                    3 -> {
                        viewModel.setSortArguments(timePeriod = "30d")
                    }
                    4 -> {
                        viewModel.setSortArguments(timePeriod = "3m")
                    }
                    5 -> {
                        viewModel.setSortArguments(timePeriod = "1y")
                    }
                    6 -> {
                        viewModel.setSortArguments(timePeriod = "3y")
                    }
                    7 -> {
                        viewModel.setSortArguments(timePeriod = "5y")
                    }
                }
                viewModel.refresh(false)
                onChangeViewsByShimmer(false)
                viewModel.onChangeSort()
            }
            avPriceFilterDropDown.setOnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        viewModel.setSortArguments(orderBy = "price", orderDirection = "desc")
                    }
                    1 -> {
                        viewModel.setSortArguments(orderBy = "price", orderDirection = "asc")
                    }
                }
                avPriceFilterDropDown.setText("Price",false)
                avPriceFilterDropDown.setSelection(avPriceFilterDropDown.text.count())
                viewModel.refresh(false)
                onChangeViewsByShimmer(false)
                viewModel.onChangeSort()
            }
        }

    }


    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false, HomeShimmer = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun onChangeViewsByShimmer(mustShow: Boolean) {
        binding?.apply {
            setShimmerIndicator(!mustShow, true)
            constParent.isVisible = mustShow
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh(true)
        onChangeViewsByShimmer(false)
    }

}