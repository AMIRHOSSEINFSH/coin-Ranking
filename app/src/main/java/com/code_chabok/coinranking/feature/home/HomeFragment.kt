package com.code_chabok.coinranking.feature.home

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseCoinAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel

import com.code_chabok.coinranking.databinding.FragmentHomeBinding
import com.code_chabok.coinranking.domain.getCoinDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : CoinFragment() {

    private var isFirstTime = true
    private var _binding: FragmentHomeBinding? = null
    private val bining: FragmentHomeBinding? get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BaseCoinAdapter
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
        setShimmerIndicator(true, HomeShimmer = true)
        setUpSpinners()
        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            showSnackBar(errorMessage)
            setShimmerIndicator(false)
            bining?.constParent?.visibility = View.VISIBLE
        }
        if (!isDetail) {
            //viewModel.backStackDetecter.value = this
        }
        adapter =
            BaseCoinAdapter(
                onUpdateClickListener = { uuid: String, isBookmark: Boolean, _: Int ->
                viewModel.updateNewBookmark(uuid, isBookmark)
            },
                onItemLongClickListener = { coinListModel ->
                    viewModel.getSpcificCoinDetail(coinListModel.uuid)
                    viewModel.coinDetailObserver
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

        viewModel.sortListLiveData.observe(viewLifecycleOwner){
            checkResponseForView(it){
                setShimmerIndicator(false)
                bining?.constParent?.visibility = View.VISIBLE
                val coinListModel: List<CoinListModel> = it.data!!
                adapter.submitList(coinListModel)
            }
        }


        //viewModel.refresh()
        viewModel.listCoins.observe(viewLifecycleOwner, {
            checkResponseForView(it) {
                viewModel.refresh(false)
                Log.i("TAG", "refreshing: ")
               // adapter.submitList(null)
                val coinListModel: List<CoinListModel> = it.data!!
                //Log.i("OnRecieved", "onViewCreated: +${coinListModel[1].name}")
                setShimmerIndicator(false)
                bining?.constParent?.visibility = View.VISIBLE
                adapter.submitList(coinListModel)
            }
        })


        bining?.rvHome?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (bining!!.rvHome.layoutManager as LinearLayoutManager).orientation
            )
        )
        bining?.rvHome?.adapter = adapter

        bining?.priceSort?.setOnClickListener {
            setShimmerIndicator(true)
            bining?.constParent?.visibility = View.GONE
            viewModel.onChangeSort(HomeViewModel.SortType.Price("price"))
        }
        _binding?.MarketCapSort?.setOnClickListener {
            setShimmerIndicator(true)
            bining?.constParent?.visibility = View.GONE
            viewModel.onChangeSort(HomeViewModel.SortType.MarketCap("marketCap"))
        }

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
                        if (!isFirstTime){
                            setShimmerIndicator(true)
                            bining?.constParent?.visibility = View.GONE
                            viewModel.onChangeSort(HomeViewModel.SortType.Time("${timeList[p2]}"))
                            isFirstTime = true
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
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeList)

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

    override fun onStart() {
        super.onStart()
        bining?.constParent?.visibility = View.VISIBLE
    }
}