package com.code_chabok.coinranking.feature.cryptoDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.databinding.FragmentCryptoDetailChildBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.navGraphViewModels
import javax.inject.Inject


@AndroidEntryPoint
class CryptoDetailChildFragment : CoinFragment() {

    /*@Inject
    val vmf: ViewModel.Factory */

    private lateinit var binding: FragmentCryptoDetailChildBinding
    private val viewModel: CryptoDetailViewModel by /*navGraphViewModels<CryptoDetailViewModel>(R.id.nav_home) {vmf}*/activityViewModels()

    private lateinit var uuuId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uuuId = viewModel.getUuid()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptoDetailChildBinding.inflate(inflater, container, false)
        viewModel.refresh(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.parent.isVisible = false
        viewModel.coinDetail.observe(viewLifecycleOwner){resource->
            checkResponseForView(resource,onSuccess = {
                binding.parent.isVisible = true
                val coinDetail = resource.data as CoinDetail
                binding.childModel = coinDetail

                val tvList = ArrayList<TextView>()

                val pattern1 = Pattern.compile("\n(.*?)\n", Pattern.DOTALL)
                val matcher1 = pattern1.matcher(coinDetail.description.toString())
                var index = 0
                while (matcher1.find()){
                    var textView = TextView(requireContext())
                    textView.setTextColor(resources.getColor(R.color.gray_600))
                    textView.id = index
                    textView.text = matcher1.group()
                    binding.leanerDescription.addView(textView)
                    Log.i("TAGAACsuc", "onViewCreated: ${matcher1.group()}")
                    tvList.add(textView)
                    index++
                }
                Log.i("TAGAACsuc", "onViewCreated: ---------------------")


            },
                onError = {
                    binding.parent.isVisible = true
                    binding.childModel = resource.data as CoinDetail
                    setShimmerIndicator(false)
            })
        }

    }


}