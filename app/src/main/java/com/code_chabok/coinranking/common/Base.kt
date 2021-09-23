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
import com.code_chabok.coinranking.R

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

    override var rootView: CoordinatorLayout? =null
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

    fun setShimmerIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->


                var loadingView = it.findViewById<View>(R.id.shimmerFrameLayout)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context)
                            .inflate(R.layout.shimmer_placeholder_layout, it, false)

                    it.addView(loadingView)

                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }

        }
    }


    abstract class CoinViewModel : ViewModel() {
        val progressBarLiveData = MutableLiveData<Boolean>()

        override fun onCleared() {
            super.onCleared()
        }

    }
}