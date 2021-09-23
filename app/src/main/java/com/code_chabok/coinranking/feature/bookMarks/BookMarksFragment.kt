package com.code_chabok.coinranking.feature.bookMarks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.repo.CryptoRepository
import com.code_chabok.coinranking.data.repo.CryptoRepositoryImp
import com.code_chabok.coinranking.data.repo.source.LocalCryptoDataSource
import com.code_chabok.coinranking.data.repo.source.RemoteCryptoDataSource
import com.code_chabok.coinranking.databinding.FragmentBookMarksBinding
import com.code_chabok.coinranking.databinding.ShimmerPlaceholderLayoutBinding
import com.code_chabok.coinranking.feature.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import android.view.ViewGroup



@AndroidEntryPoint
class BookMarksFragment : CoinFragment() {

    private lateinit var binding: FragmentBookMarksBinding
    private lateinit var shimmerBinding: ShimmerPlaceholderLayoutBinding


     val viewModel: BookMarksViewModel by viewModels()

    @Inject
    lateinit var adapter : BookMarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentBookMarksBinding.inflate(inflater, container, false)
        shimmerBinding = ShimmerPlaceholderLayoutBinding.inflate(inflater, container, false)
        rootView = binding.root as CoordinatorLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setShimmerIndicator(true)
        if (savedInstanceState !=null){
            //setShimmerIndicator(false)

            //rootView?.removeView(shimmerBinding.loading)

            //rootView?.removeView(shimmerBinding.loading)
            //rootView?.removeAllViews()
            //((shimmerBinding.loading.getParent()) as ViewGroup).removeView(shimmerBinding.loading)
            //it.addView(loadingView)
            //setShimmerIndicator(true)
            /*var loadingView =  rootView?.findViewById<CoordinatorLayout>(R.id.loading)
            rootView?.removeView(loadingView)*/
            //setShimmerIndicator(true)
        }


        adapter.setActivity(requireActivity())
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

            //shimmerBinding.shimmerFrameLayout.visibility = View.GONE
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

    override fun onPause() {
        super.onPause()
        //setShimmerIndicator(false)
        //(rootView as ViewGroup).removeView(shimmerBinding.loading)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //rootView?.removeAllViews()
        //setShimmerIndicator(false)
    }


}