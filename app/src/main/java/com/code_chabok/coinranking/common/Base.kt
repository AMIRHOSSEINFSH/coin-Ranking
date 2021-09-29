package com.code_chabok.coinranking.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.feature.CryptoDetail.CryptoDetailChildFragment
import com.code_chabok.coinranking.feature.bookMarks.BookMarksFragment
import com.code_chabok.coinranking.feature.exchanges.ExchangeDetailChildFragment
import com.code_chabok.coinranking.feature.exchanges.ExchangesFragment
import com.code_chabok.coinranking.feature.home.HomeFragment

abstract class CoinFragment : Fragment(), CoinView {
    override var rootView: CoordinatorLayout? = null
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}

abstract class CoinActivity : AppCompatActivity(), CoinView {

    override var rootView: CoordinatorLayout? = null
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView must be instance of CoordinatorLayout")
            } else
                return viewGroup
        }
    override val viewContext: Context?
        get() = this

}

interface CoinView {
    var rootView: CoordinatorLayout?
    val viewContext: Context?

    fun setShimmerIndicator(mustShow: Boolean, coinView: Boolean = false) {
        rootView?.let {
            viewContext?.let { context ->


                var loadingView = it.findViewById<View>(R.id.shimmerFrameLayout)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context)
                            .inflate(
                                if (coinView) R.layout.shimmer_coin_page_place_holder
                                else R.layout.shimmer_placeholder_layout,
                                it,
                                false
                            )

                    it.addView(loadingView)

                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }

        }
    }


    abstract class CoinViewModel : ViewModel() {
        val backStackDetecter = MutableLiveData<Fragment>()

        val progressBarLiveData = MutableLiveData<Boolean>()

        override fun onCleared() {
            super.onCleared()
        }

    }
}

abstract class BaseFragmentAdapter(fragment: CoinFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }
}

class FragmentAdapterCrypto(fragment: CoinFragment) : BaseFragmentAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CryptoDetailChildFragment()
            1 -> ExchangesFragment().isInDetail(true)
            else -> CryptoDetailChildFragment()
        }
    }

}

class FragmentAdapterExchange(fragment: CoinFragment) : BaseFragmentAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ExchangeDetailChildFragment()
            1 -> HomeFragment().isInDetail(true)
            else -> ExchangeDetailChildFragment()
        }
    }

}

var isDetail = false

//class FragmentAdapterCryptoHomeStack(fra)
