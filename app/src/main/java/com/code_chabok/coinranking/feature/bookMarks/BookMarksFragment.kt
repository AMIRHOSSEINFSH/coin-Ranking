package com.code_chabok.coinranking.feature.bookMarks

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseCoinAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.common.isDetail
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
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentBookMarksBinding.inflate(inflater, container, false)
        rootView = binding.root as CoordinatorLayout

        return binding.root
    }

    //private var isDetail = false
    fun isInDetail(boolean: Boolean): BookMarksFragment {
        isDetail = boolean
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setShimmerIndicator(true)

        adapter =
            BaseCoinAdapter(onUpdateClickListener = { uuid: String, isBookmark: Boolean, position: Int ->
                viewModel.updateNewBookmark(uuid, isBookmark)
                adapter.notifyItemRemoved(position)
            },
                onItemLongClickListener = { coinListModel ->
                    //viewModel.getSpcificCoinDetail(coinListModel.uuid)
                    viewModel.coinDetailObserver
                }, onChangeDir = { isDetail: Boolean, position: Int ->
                    val bundle = Bundle().apply {
                        //putParcelable("item", item)
                        putString("uuid", adapter.currentList[position].coin.uuid)
                    }
                    if (!isDetail)
                        findNavController().navigate(R.id.home_book_same, bundle)
                    else
                        findNavController().navigate(R.id.action_same_to_same, bundle)

                })
        //adapter.setActivity(requireActivity())
        adapter.apply {
            setActivity(requireActivity())
            setIsDetail(isDetail)
            showBubble = savedInstanceState == null
        }
        binding.rvBookmark.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.listCoin.observe(viewLifecycleOwner) { list ->
            setShimmerIndicator(false)
            val mainList = list.filter {
                it.bookmark != null
            }
            if (mainList.isNotEmpty()) {

                adapter.submitList(list.filter {
                    it.bookmark != null
                })
                binding.rvBookmark.visibility = View.VISIBLE
            } else {
                binding.lottieAnimation.visibility = View.VISIBLE
                binding.rvBookmark.visibility = View.GONE
            }
        }

        binding.rvBookmark.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rvBookmark.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvBookmark.adapter = adapter


    }

    override fun onPause() {
        super.onPause()
        isInDetail(true)
    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}
