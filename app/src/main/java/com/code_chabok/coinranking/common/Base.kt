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
import com.code_chabok.coinranking.feature.cryptoDetail.CryptoDetailChildFragment
import com.code_chabok.coinranking.feature.exchanges.ExchangeDetailChildFragment
import com.code_chabok.coinranking.feature.exchanges.ExchangesFragment
import com.code_chabok.coinranking.feature.home.HomeFragment
import com.google.android.material.snackbar.Snackbar

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

    fun setShimmerIndicator(mustShow: Boolean, HomeShimmer: Boolean = false,DetailPage: Boolean = false) {
        rootView?.let {
            viewContext?.let { context ->

                var loadingView = it.findViewById<View>(R.id.shimmerFrameLayout)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context)
                            .inflate(
                                when {
                                    HomeShimmer -> R.layout.shimmer_coin_page_place_holder
                                    DetailPage -> R.layout.shimmer_detail_page
                                    else -> R.layout.shimmer_placeholder_layout
                                },
                                it,
                                false
                            )

                    it.addView(loadingView)

                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }

        }
    }

    fun <T> checkResponseForView(
        resource: Resource<T>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        when (resource) {
            is Resource.Success -> {
                //setShimmerIndicator(false)
                onSuccess()
            }
            is Resource.Error -> {

                showSnackBar(resource.message!!)
                //setShimmerIndicator(false)
                if (resource.message != "refreshing is Lock!!"){
                    onError()
                }
            }
            is Resource.Loading -> {
                //setShimmerIndicator(true)
            }
        }
    }

    fun showSnackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT,
    ): Snackbar? {
        rootView?.let { coordinatorLayout ->
            val snackbar = Snackbar.make(coordinatorLayout, message, duration)
            snackbar.show()
            return snackbar
        }
        return null
    }

}


abstract class CoinViewModel : ViewModel() {

    protected val refreshing = MutableLiveData(true)

    val errorLiveData = MutableLiveData<String>()

    fun refresh(shouldRefresh: Boolean) {
        refreshing.value = shouldRefresh
    }


    val backStackDetecter = MutableLiveData<Fragment>()

    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
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

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}





