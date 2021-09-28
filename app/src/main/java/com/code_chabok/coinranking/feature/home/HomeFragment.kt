package com.code_chabok.coinranking.feature.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.databinding.FragmentHomeBinding
import com.code_chabok.coinranking.feature.bookMarks.BookMarkAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : CoinFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val bining: FragmentHomeBinding? get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BookMarkAdapter
    private val priceList = arrayListOf("2000", "3000", "4000")
    private val timeList = arrayListOf("1h", "24h", "7d", "1m", "1y")
    private val marketList = arrayListOf("marketCap", "C++")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rootView = bining?.root as CoordinatorLayout

        return bining!!.root
    }

    private var isDetail = true
//    fun isInDetail(boolean: Boolean):HomeFragment{
//        isDetail = boolean
//        return this
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShimmerIndicator(true)
        setUpSpinners()
        adapter = BookMarkAdapter {

            Log.i("TAG", "onViewCreated:${isDetail} ")
            val bundle = Bundle().apply {
                putParcelable("item", it)
            }

            if (isDetail) {
                findNavController().navigate(
                    R.id.action_homeFragment_to_cryptoDetailChildFragment,
                    bundle
                )
//                //BookMarksFragmentDirections.action_cryptoDetailFragment_self(it)
            } else {
                findNavController().navigate(
                    R.id.action_homeFragment_to_cryptoDetailFragment2,
                    bundle
                )
            }
        }

        adapter.setActivity(requireActivity())
        adapter.apply {
            setActivity(requireActivity())
            showBubble = savedInstanceState == null
        }
        bining?.rvHome?.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.cryptoListLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            setShimmerIndicator(false)
            bining?.rvHome?.visibility = View.VISIBLE
        })


        bining?.rvHome?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (bining!!.rvHome.layoutManager as LinearLayoutManager).orientation
            )
        )
        bining?.rvHome?.adapter = adapter

    }

    fun setUpSpinners() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            _binding?.apply {
                priceSpinner.alpha = 0.5F
                timeSpinner.alpha = 0.5F
                marketCapSpinner.alpha = 0.5F
            }
        }
        _binding?.priceSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                var isUp = false
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    ((p0?.getChildAt(0)) as TextView).apply {
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.spinnerBlack))
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12F)
                    }
                    if (!isUp) {
                        _binding?.ivPriceArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                    } else {
                        _binding?.ivPriceArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                        isUp = true
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        _binding?.timeSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                var isUp = false
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    ((p0?.getChildAt(0)) as TextView).apply {
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.spinnerBlack))
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12F)
                    }
                    if (!isUp) {
                        _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                    } else {
                        _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                        isUp = true
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        _binding?.marketCapSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                var isUp = false
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    ((p0?.getChildAt(0)) as TextView).apply {
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.spinnerBlack))
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12F)
                    }
                    if (!isUp) {
                        _binding?.ivMarketCapArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                    } else {
                        _binding?.ivMarketCapArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                        isUp = true
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        var priceListAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priceList)
        var timeListAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeList)
        var marketCapAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, marketList)

        _binding?.apply {
            priceSpinner.adapter = priceListAdapter
            timeSpinner.adapter = timeListAdapter
            marketCapSpinner.adapter = marketCapAdapter
        }

        _binding?.priceSpinner?.setOnTouchListener { view, motionEvent ->

            if (MotionEvent.ACTION_DOWN == motionEvent.action) {
                _binding?.ivPriceArrow?.setImageResource(R.drawable.ic_arrow_down_spinner)
            }
            view.performClick()
        }

        _binding?.timeSpinner?.setOnTouchListener { view, motionEvent ->

            if (MotionEvent.ACTION_DOWN == motionEvent.action) {
                _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_down_spinner)
            }
            view.performClick()
        }

        _binding?.timeSpinner?.setOnTouchListener { view, motionEvent ->

            if (MotionEvent.ACTION_DOWN == motionEvent.action) {
                _binding?.ivMarketCapArrow?.setImageResource(R.drawable.ic_arrow_down_spinner)
            }
            view.performClick()
        }
    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}