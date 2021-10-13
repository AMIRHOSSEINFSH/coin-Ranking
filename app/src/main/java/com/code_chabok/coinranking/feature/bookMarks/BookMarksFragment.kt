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
    ): View? {
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
            },onChangeDir = {isDetail: Boolean, position: Int ->
                    val bundle = Bundle().apply {
                        //putParcelable("item", item)
                        putString("uuid", adapter.currentList[position].uuid)
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
        binding.rec.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.listCoin.observe(viewLifecycleOwner) { list ->
            setShimmerIndicator(false)
            if (list.isNotEmpty()) {
                adapter.submitList(list)
                binding.rec.visibility = View.VISIBLE
            }
            else{
                binding.lottieAnimation.visibility = View.VISIBLE
                binding.rec.visibility = View.GONE
            }
        }


        binding.rec.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rec.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rec.adapter = adapter


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
        //Toast.makeText(requireContext(), "onDestroyView called", Toast.LENGTH_SHORT).show()
        //rootView?.removeAllViews()
        //setShimmerIndicator(false)
    }


}
