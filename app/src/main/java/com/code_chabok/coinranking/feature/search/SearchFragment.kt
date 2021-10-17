package com.code_chabok.coinranking.feature.search

import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseCoinAdapter
import com.code_chabok.coinranking.common.BaseExchangeAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.code_chabok.coinranking.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class SearchFragment : CoinFragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var coinAdapter: BaseCoinAdapter
    private lateinit var exchangeAdapter: BaseExchangeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.mi_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinAdapter =
            BaseCoinAdapter(onUpdateClickListener = { uuid: String, isBookmark: Boolean, _: Int ->
                viewModel.updateNewBookmark(uuid, isBookmark)
            }, onItemLongClickListener = { coinListModel ->
                //viewModel.getSpcificCoinDetail(coinListModel.uuid)
                viewModel.coinDetailObserver
            }, onChangeDir = { isDetail: Boolean, position: Int ->
                val bundle = Bundle().apply {
                    //putParcelable("item", item)
                    putString("uuid", coinAdapter.currentList[position].coin.uuid)
                }
                findNavController().navigate(R.id.action_searchFragment_to_cryptoDetailFragment,bundle)
                /*if (!isDetail)
                    findNavController().navigate(R.id.home_book_same, bundle)
                else
                    findNavController().navigate(R.id.action_same_to_same, bundle)*/

            })

        exchangeAdapter =
            BaseExchangeAdapter(
                onItemLongClickListener = {

                },
                onChangeDir = { isDetail: Boolean, position: Int ->
                    val bundle = Bundle().apply {
                        putString("uuid", coinAdapter.currentList[position].coin.uuid)
                    }
                    findNavController().navigate(R.id.action_searchFragment_to_exchangeDetailFragment,bundle)
                    /*if (!isDetail)
                        findNavController().navigate(R.id.home_book_same, bundle)
                    else
                        findNavController().navigate(R.id.action_same_to_same, bundle)*/
                },onActivityProvider = {
                    requireActivity()
                })


        coinAdapter.setActivity(requireActivity())
        coinAdapter.apply {
            setActivity(requireActivity())
            //setIsDetail(isDetail)
            showBubble = savedInstanceState == null
        }
        binding.rvCoinSearch.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }
        binding.rvExchangeSearch.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }
        binding.rvCoinSearch.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rvCoinSearch.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvExchangeSearch.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.rvCoinSearch.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvCoinSearch.adapter = coinAdapter
        binding.rvExchangeSearch.adapter = exchangeAdapter
        search()


    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false)
    }

    fun search() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.search(text.toString().trim())
        }

        viewModel.resultSearchResource.observe(viewLifecycleOwner) {

            val coinListModel: List<CoinAndBookmark> = it.coins!!
            val exchangeList: List<Exchange> = it.exchanges!!
            if (binding.etSearch.text.isEmpty()) {
                runBlocking {
                    delay(3000)
                    withContext(Dispatchers.Main){
                        setShimmerIndicator(true)
                    }
                }
                coinAdapter.submitList(null)
                exchangeAdapter.submitList(null)
            } else {
                setShimmerIndicator(false)
                coinAdapter.submitList(coinListModel)
                exchangeAdapter.submitList(exchangeList)
            }
            binding.constParent.visibility = View.VISIBLE

        }
    }
}