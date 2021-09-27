package com.code_chabok.coinranking.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BookMarkAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
       rootView = binding.root as CoordinatorLayout

        return binding.root
    }

    private var isDetail = true
//    fun isInDetail(boolean: Boolean):HomeFragment{
//        isDetail = boolean
//        return this
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShimmerIndicator(true)

        adapter = BookMarkAdapter {

            Log.i("TAG", "onViewCreated:${isDetail} ")
            val bundle = Bundle().apply {
                putParcelable("item", it)
            }

            if (isDetail) {
                findNavController().navigate(R.id.action_homeFragment_to_cryptoDetailChildFragment,
                    bundle
                )
//                //BookMarksFragmentDirections.action_cryptoDetailFragment_self(it)
            }
                else {
                    findNavController().navigate(R.id.action_homeFragment_to_cryptoDetailFragment2, bundle)
            }
                }

                adapter.setActivity(requireActivity())
                adapter.apply {
                    setActivity(requireActivity())
                    showBubble = savedInstanceState == null
                }
                binding.rvHome.layoutManager =
                    object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                        override fun canScrollVertically(): Boolean {
                            return true
                        }
                    }

                viewModel.cryptoListLiveData.observe(viewLifecycleOwner, {
                    adapter.submitList(it)
                setShimmerIndicator(false)
                    binding.rvHome.visibility = View.VISIBLE
                })


                binding.rvHome.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        (binding.rvHome.layoutManager as LinearLayoutManager).orientation
                    )
                )
                binding.rvHome.adapter = adapter


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