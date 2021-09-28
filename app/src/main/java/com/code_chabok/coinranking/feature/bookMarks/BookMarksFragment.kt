package com.code_chabok.coinranking.feature.bookMarks

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseCoinAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.databinding.FragmentBookMarksBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookMarksFragment : CoinFragment() {

    private lateinit var binding: FragmentBookMarksBinding
    private val viewModel: BookMarksViewModel by viewModels()
    lateinit var adapter: BaseCoinAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentBookMarksBinding.inflate(inflater, container, false)
        rootView = binding.root as CoordinatorLayout

        return binding.root
    }
    private var isDetail = false
    fun isInDetail(boolean: Boolean):BookMarksFragment{
        isDetail = boolean
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setShimmerIndicator(true)

        adapter = BaseCoinAdapter {

            Log.i("TAG", "onViewCreated:${isDetail} ")
            val bundle = Bundle().apply {
                putParcelable("item",it)
            }

            if(isDetail){
                findNavController().navigate(R.id.action_cryptoDetailFragment_to_exchangeDetailFragment2,bundle)
                //BookMarksFragmentDirections.action_cryptoDetailFragment_self(it)
            }
            else {
                findNavController().navigate(
                    R.id.action_bookMarksFragment_to_cryptoDetailFragment, bundle
                    /*BookMarksFragmentDirections.actionBookMarksFragmentToCryptoDetailFragment(
                        it
                    )*/
                )
            }
        }
        //adapter.setActivity(requireActivity())
        adapter.apply {
            setActivity(requireActivity())
            showBubble = savedInstanceState == null
        }
        binding.rec.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.cryptoListLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            setShimmerIndicator(false)
            binding.rec.visibility = View.VISIBLE
        })


        binding.rec.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rec.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rec.adapter = adapter


    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //rootView?.removeAllViews()
        //setShimmerIndicator(false)
    }


}
