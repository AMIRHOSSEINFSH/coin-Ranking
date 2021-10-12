package com.code_chabok.coinranking.feature.exchanges

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseExchangeAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.databinding.FragmentExchangesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangesFragment : CoinFragment() {

    private lateinit var binding: FragmentExchangesBinding
    val viewModel: ExchangesViewModel by viewModels()
    private lateinit var adapter: BaseExchangeAdapter

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
        if (!isDetail)

            adapter = BaseExchangeAdapter {
                Log.i("TAG", "onViewCreated:${isDetail} ")
                val bundle = Bundle().apply {
                    putParcelable("item", it)
                }
                if (isDetail)
                    findNavController().navigate(R.id.action_same_to_same, bundle)
                else {
                    findNavController().navigate(
                        ExchangesFragmentDirections.actionExchangesFragmentToExchangeDetailFragment(
                            it.uuid
                        )
                    )
                }
                //viewModel.backStackDetecter.observe(viewLifecycleOwner,{ backStackFragment->
                /*if (baseFragment is baseFragmentHolder.Home){*//*action_exchangeDetailFragment_to_cryptoDetailFragment3*//*
                    if (!(baseFragment as baseFragmentHolder.Home).isLoop)
                    findNavController().navigate(R.id.action_cryptoDetailFragment2_to_exchangeDetailFragment3,bundle)

                    else {
                        findNavController().navigate(
                            R.id.action_exchangeDetailFragment3_to_cryptoDetailFragment2,
                            bundle
                        )
                        (baseFragment as baseFragmentHolder.Home).isLoop = false
                    }
                }
                else if(baseFragment is baseFragmentHolder.Exchange){
                    if (isDetail){
                        findNavController().navigate(R.id.action_exchangeDetailFragment_to_cryptoDetailFragment3,bundle)
                    }
                    else{*//*action_cryptoDetailFragment2_to_exchangeDetailFragment3*//*
                        findNavController().navigate(R.id.action_exchangesFragment_to_exchangeDetailFragment,bundle)
                    }
                }*/
                //})


                //findNavController().navigate(R.id.action_exchangeDetailFragment_to_cryptoDetailFragment3,bundle)
                //BookMarksFragmentDirections.action_cryptoDetailFragment_self(it)
//            else if (isDetail){
//                findNavController().navigate(R.id.action_cryptoDetailFragment3_to_exchangeDetailFragment,bundle)
//            }
//            else
//                findNavController().navigate(
//                    R.id.action_exchangesFragment_to_exchangeDetailFragment,bundle
//                    /*BookMarksFragmentDirections.actionBookMarksFragmentToCryptoDetailFragment(
//                        it
//                    )*/
//                )
            }.apply {
                setActivity(requireActivity())
            }
        binding.rec.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.exchangeResource.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                adapter.submitList(it.data)
                setShimmerIndicator(false)
                binding.rec.visibility = View.VISIBLE

            }

        }

        binding.rec.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rec.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rec.adapter = adapter


        /*binding.tv.setOnClickListener {
            findNavController().navigate(R.id.action_exchangesFragment_to_searchFragment2)
        }*/
    }


    companion object {
        const val TAG = "TAGFRAGMENT"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: ")
    }


}