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
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import javax.inject.Inject


@AndroidEntryPoint
class CryptoDetailChildFragment : CoinFragment() {

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
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()

    }

    private fun observe() {
        viewModel.coinDetailOnSort.observe(viewLifecycleOwner) { resource ->
            checkResponseForView(resource, onSuccess = {
                onChangeViewsByShimmer(true)
                val model = resource.data as CoinAndBookmark
                binding.childModel = model
                getOptimizedText(model.coin.description.toString())
            }, onError = {
                onChangeViewsByShimmer(true)
                val model = resource.data as CoinAndBookmark
                binding.childModel = model
                getOptimizedText(model.coin.description.toString())
            }
            )
        }
    }

    private fun onChangeViewsByShimmer(mustShow: Boolean) {

        binding.apply {
            setShimmerIndicator(!mustShow, DetailPage = true)
            parent.isVisible = mustShow
        }

    }

    private fun getOptimizedText(description: String){
        binding.leanerDescription.removeAllViews()
        val tvList = ArrayList<TextView>()

        val pattern1 = Pattern.compile("\n(.*?)\n", Pattern.DOTALL)
        val matcher1 = pattern1.matcher(description)
        var index = 0
        while (matcher1.find()) {
            var textView = TextView(requireContext())
            textView.setTextColor(resources.getColor(R.color.gray_600))
            textView.id = index
            textView.text = matcher1.group()
            binding.leanerDescription.addView(textView)
            Log.i("TAGAACsuc", "onViewCreated: ${matcher1.group()}")
            tvList.add(textView)
            index++
        }
    }


}