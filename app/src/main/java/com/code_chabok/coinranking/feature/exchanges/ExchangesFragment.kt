package com.code_chabok.coinranking.feature.exchanges

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseExchangeAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.databinding.FragmentExchangesBinding
import com.nabilmh.lottieswiperefreshlayout.LottieSwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangesFragment : CoinFragment() {

    private lateinit var binding: FragmentExchangesBinding
    val viewModel: ExchangesViewModel by viewModels()
    private lateinit var adapter: BaseExchangeAdapter
    private lateinit var swipeRefreshLayout: LottieSwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExchangesBinding.inflate(inflater, container, false)
        return binding.root


    }


    private var isDetail = false

    fun isInDetail(boolean: Boolean): ExchangesFragment {
        isDetail = boolean

        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShimmerIndicator(true)
        swipeRefreshLayout = binding.swipeRefresh
        //if (!isDetail)

        swipeRefreshLayout.setOnRefreshListener {
            binding.rvExchange.visibility = View.VISIBLE
            viewModel.refresh(true)
            setShimmerIndicator(true)
            swipeRefreshLayout.isRefreshing = false
        }


        adapter = BaseExchangeAdapter( onChangeDir = {isDetail: Boolean, position: Int ->
                val bundle = Bundle().apply {
                    //putParcelable("item", item)
                    putString("uuid", adapter.currentList[position].uuid)
                }

                if (isDetail)
                    findNavController().navigate(R.id.action_same_to_same, bundle)
                else {
                    findNavController().navigate(
                        ExchangesFragmentDirections.actionExchangesFragmentToExchangeDetailFragment(
                            adapter.currentList[position].uuid
                        )
                    )
                }

            },onItemLongClickListener = {

            },onActivityProvider = {
                requireActivity()
            })
        binding.rvExchange.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.exchangeList.observe(viewLifecycleOwner) {
            checkResponseForView(it,onSuccess = {
                adapter.submitList(it.data.orEmpty())
                setShimmerIndicator(false)
                binding.rvExchange.visibility = View.VISIBLE
            },onError = {
                setShimmerIndicator(false)
                binding.rvExchange.isVisible = true
                adapter.submitList(it.data.orEmpty())
            })
        }

        binding.rvExchange.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rvExchange.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvExchange.adapter = adapter

    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
    }


}