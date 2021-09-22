package com.code_chabok.coinranking.feature.bookMarks

import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.databinding.FragmentBookMarksBinding
import com.code_chabok.coinranking.databinding.ShimmerPlaceholderLayoutBinding
import kotlinx.coroutines.*


class BookMarksFragment : CoinFragment() {

    private lateinit var binding: FragmentBookMarksBinding
    private lateinit var shimmerBinding: ShimmerPlaceholderLayoutBinding
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
        val array = arrayListOf<Crypto>(
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "1"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "2"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "3"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "4"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "5"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "6"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "7"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "8"
            ),
            Crypto(
                "Bitcoin",
                "BTC",
                "2.50975",
                "15%",
                true,
                "dusd",
                "fdhuvd",
                false,
                img_url = "https://example.com",
                "9"
            )
        )


        val adapter = BookMarkAdapter({

        }, {

        },requireActivity())
        binding.rec.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        lifecycleScope.launchWhenCreated {
            delay(3000)
            withContext(Dispatchers.Main) {
                adapter.submitList(array)
                setShimmerIndicator(false)

                shimmerBinding.shimmerFrameLayout.visibility = View.GONE
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


    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
    }

    override fun onPause() {
        super.onPause()
        setShimmerIndicator(false)
    }


}