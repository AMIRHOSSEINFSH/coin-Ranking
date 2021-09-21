package com.code_chabok.coinranking.feature.bookMarks

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.databinding.FragmentBookMarksBinding


class BookMarksFragment : CoinFragment() {

    private lateinit var binding: FragmentBookMarksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        //val adapter = BookMarkAdapter(array)

        val adapter = BookMarkAdapter({

        },{

        })
        binding.rec.layoutManager =  object : LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false){
            override fun canScrollVertically(): Boolean { return true }
        }

        adapter.submitList(array)
        binding.rec.adapter = adapter



    }


}