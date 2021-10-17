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

@AndroidEntryPoint
class HomeFragment : CoinFragment(), OnChangeSort {

    enum class isSortOn {
        PRICE, TIME, MARKETCAP, NOTHING
    }

    private var isSet = isSortOn.NOTHING
    private var isFirstTime = true
    private var _binding: FragmentHomeBinding? = null
    private val bining: FragmentHomeBinding? get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BaseCoinAdapter
    private lateinit var swipeRefreshLayout: LottieSwipeRefreshLayout
    private val dialog = MySortDialog()

    private val timeList = arrayListOf("3h", "24h", "7d", "30d", "3m", "1y", "3y", "5y")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rootView = bining?.root as CoordinatorLayout

        return bining!!.root
    }

    private var isDetail = false
    fun isInDetail(boolean: Boolean): HomeFragment {
        isDetail = boolean
        return this
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog.isCancelable = true
        //viewModel.refresh(true)
        //onChangeViewsByShimmer(false)
        swipeRefreshLayout = _binding!!.swipeRefresh
        setUpSpinners()

        swipeRefreshLayout.setOnRefreshListener {
            onChangeViewsByShimmer(false)
            viewModel.refresh(true)
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.sortListLiveData.observe(viewLifecycleOwner) {
            checkResponseForView(it, onSuccess = {
                val coinListModel: List<CoinAndBookmark> = it.data as List<CoinAndBookmark>
                //onChangeViewsByShimmer(true)
                setShimmerIndicator(false)
                _binding?.apply {
                    constParent.isVisible = true
                    priceFrame.isVisible = true
                    timeFrame.isVisible = true
                    marketCapFrame.isVisible = true
                }
                viewModel.refresh(false)
                adapter.submitList(coinListModel) {
                    bining?.rvHome?.smoothScrollToPosition(0)
                }
            }, onError = {
                onChangeViewsByShimmer(true)
                viewModel.refresh(false)
                adapter.submitList(it.data as MutableList<CoinAndBookmark>?) {
                    bining?.rvHome?.smoothScrollToPosition(0)
                }
            })
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            showSnackBar(errorMessage)
            onChangeViewsByShimmer(true)
            viewModel.refresh(false)
        }
        /*bining?.apply {
            priceFrame.visibility = View.GONE
            priceFrame.isVisible = !isDetail
            timeFrame.isVisible = !isDetail
            marketCapFrame.isVisible = !isDetail
        }*/
        if (isDetail) {
        }
        adapter =
            BaseCoinAdapter(
                onUpdateClickListener = { uuid: String, isBookmark: Boolean, _: Int ->
                    viewModel.updateNewBookmark(uuid, isBookmark)
                },
                onItemLongClickListener = { coinListModel ->
                    viewModel.refresh(true)
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
        bining?.rvHome?.layoutManager =
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
                   // bining?.rvHome?.smoothScrollToPosition(0)
                }

            }, onError = {
                viewModel.refresh(false)
                onChangeViewsByShimmer(true)
                adapter.submitList(it.data as MutableList<CoinAndBookmark>?) {
                    bining?.rvHome?.smoothScrollToPosition(0)
                }
            })
        })


        bining?.rvHome?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (bining!!.rvHome.layoutManager as LinearLayoutManager).orientation
            )
        )
        bining?.rvHome?.adapter = adapter

        bining?.priceSort?.setOnClickListener {
            isSet = isSortOn.PRICE

            _binding?.apply {
                if (priceFrame.tag == "R.drawable.bg_spinner_framelayout_unselected") {
                    dialog.show(childFragmentManager, null)

                    priceFrame.tag = "R.drawable.bg_spinner_framelayout_selected"
                    priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_selected)
                    priceSort.setTextColor(resources.getColor(R.color.white))
                } else {
                    setShimmerIndicator(true)
                    viewModel.refresh(true)
                    priceSort.setTextColor(resources.getColor(R.color.spinnerBlack))
                    //viewModel.refresh(false)
                    priceFrame.tag = "R.drawable.bg_spinner_framelayout_unselected"
                    priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                }
                marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
            }

        }
        _binding?.marketCapFrame?.setOnClickListener {
            isSet = isSortOn.MARKETCAP
            _binding?.apply {
                if (marketCapFrame.tag == "R.drawable.bg_spinner_framelayout_unselected") {
                    marketCapFrame.tag = "R.drawable.bg_spinner_framelayout_selected"
                    marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_selected)
                    MarketCapSort.setTextColor(resources.getColor(R.color.white))
                    dialog.show(childFragmentManager, null)
                } else {
                    setShimmerIndicator(true)
                    viewModel.refresh(true)
                    marketCapFrame.tag = "R.drawable.bg_spinner_framelayout_unselected"
                    marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                    MarketCapSort.setTextColor(resources.getColor(R.color.spinnerBlack))
                }
                priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
            }
        }

        setUpNavigationSelection()
    }

    private fun setUpNavigationSelection() {

    }

    fun setUpSpinners() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            _binding?.apply {
                timeSpinner.alpha = 0.5F
            }
        }

        _binding?.timeSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                var isUp = false
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!isUp) {
                        if (!isFirstTime) {
                            isSet = isSortOn.TIME
                            _binding?.apply {
                                timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_selected)
                                priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                                marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                            }
                            onChangeViewsByShimmer(false)
                            viewModel.refresh(false)
                            viewModel.onChangeSort(HomeViewModel.SortType.Time("${timeList[p2]}"))
                        } else {
                            _binding?.apply {
                                timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                                priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                                marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                            }
                            isFirstTime = false
                        }
                        _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                    } else {
                        _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                        isUp = true
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        var timeListAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeList)

        _binding?.apply {
            timeSpinner.adapter = timeListAdapter
        }


        _binding?.timeSpinner?.setOnTouchListener { view, motionEvent ->

            if (MotionEvent.ACTION_DOWN == motionEvent.action) {
                _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_down_spinner)
            }
            view.performClick()
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

    override fun onResult(isDesc: Boolean) {
        onChangeViewsByShimmer(false)
        viewModel.refresh(false)
        when (isSet) {
            isSortOn.PRICE -> {
                viewModel.onChangeSort(HomeViewModel.SortType.Price("price", isDesc))
            }
            isSortOn.TIME -> {
            }
            isSortOn.MARKETCAP -> {
                viewModel.onChangeSort(HomeViewModel.SortType.MarketCap("marketCap", isDesc))
            }
        }
    }

    override fun onCancel() {
        onChangeViewsByShimmer(true)
        _binding?.apply {
            timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
            priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
            marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
        }
    }

    private fun onChangeViewsByShimmer(mustShow: Boolean) {
        bining?.apply {
            setShimmerIndicator(!mustShow, true)
            constParent.isVisible = mustShow
            priceFrame.isVisible = mustShow
            timeFrame.isVisible = mustShow
            marketCapFrame.isVisible = mustShow
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh(true)
        onChangeViewsByShimmer(false)
    }

}