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

import com.code_chabok.coinranking.databinding.FragmentHomeBinding
import com.code_chabok.coinranking.domain.getCoinDetail
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
        setShimmerIndicator(true, HomeShimmer = true)
        dialog.isCancelable = true
        dialog.isCancelable = false
        viewModel.refresh(true)
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
                }, onChangeDir = { isDetail: Boolean, position: Int ->
                    val bundle = Bundle().apply {
                        //putParcelable("item", item)
                        putString("uuid", adapter.currentList[position].uuid)
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

        viewModel.sortListLiveData.observe(viewLifecycleOwner) {
            checkResponseForView(it) {
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
            isSet = isSortOn.PRICE

            /*setShimmerIndicator(true)
            bining?.constParent?.visibility = View.GONE
            viewModel.onChangeSort(HomeViewModel.SortType.Price("price"))*/

            _binding?.apply {
                if (priceFrame.tag == "R.drawable.bg_spinner_framelayout_unselected") {
                    dialog.show(childFragmentManager,null)
                    setShimmerIndicator(true)
                    constParent.visibility = View.GONE
                    priceFrame.tag = "R.drawable.bg_spinner_framelayout_selected"
                    priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_selected)
                    priceSort.setTextColor(resources.getColor(R.color.white))
                } else {
                    priceSort.setTextColor(resources.getColor(R.color.spinnerBlack))
                    viewModel.refresh(true)
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
                    setShimmerIndicator(true)
                    constParent.visibility = View.GONE
                    marketCapFrame.tag = "R.drawable.bg_spinner_framelayout_selected"
                    marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_selected)
                    MarketCapSort.setTextColor(resources.getColor(R.color.white))
                    dialog.show(childFragmentManager,null)
                } else {
                    viewModel.refresh(true)
                    marketCapFrame.tag = "R.drawable.bg_spinner_framelayout_unselected"
                    marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                    MarketCapSort.setTextColor(resources.getColor(R.color.spinnerBlack))
                }
                priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
            }
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
                        if (!isFirstTime) {
                            isSet = isSortOn.TIME
                            _binding?.apply {
                                timeFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_selected)
                                priceFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                                marketCapFrame.setBackgroundResource(R.drawable.bg_spinner_framelayout_unselected)
                            }
                            setShimmerIndicator(true)
                            bining?.constParent?.visibility = View.GONE
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

    override fun onResult(isDesc: Boolean) {
        when(isSet){
             isSortOn.PRICE->{viewModel.onChangeSort(HomeViewModel.SortType.Price("price",isDesc))}
            isSortOn.TIME ->{}
            isSortOn.MARKETCAP->{viewModel.onChangeSort(HomeViewModel.SortType.MarketCap("marketCap",isDesc))}
        }
    }

}