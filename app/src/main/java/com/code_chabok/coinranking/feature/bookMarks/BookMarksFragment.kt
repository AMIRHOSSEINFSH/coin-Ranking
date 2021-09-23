package com.code_chabok.coinranking.feature.bookMarks

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
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


@AndroidEntryPoint
class BookMarksFragment : CoinFragment() {

    private lateinit var binding: FragmentBookMarksBinding

    val viewModel: BookMarksViewModel by viewModels()

    @Inject
    lateinit var adapter: BookMarkAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentBookMarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerFrameLayout.startShimmer()
        binding.rec.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        viewModel.cryptoListLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.apply {
                rec.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmer()
            }

        })


        binding.rec.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rec.layoutManager as LinearLayoutManager).orientation
            )
        )
        adapter.setActivity(requireActivity())
        binding.rec.adapter = adapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerFrameLayout.stopShimmer()
        /*binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.visibility = View.GONE*/
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


}