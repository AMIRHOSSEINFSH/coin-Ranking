package com.code_chabok.coinranking.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code_chabok.coinranking.R
import com.facebook.shimmer.ShimmerFrameLayout

abstract class CoinFragment: Fragment(),CoinView{
    override var rootView: CoordinatorLayout? = null
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context
}

abstract class CoinActivity: AppCompatActivity(),CoinView{

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

interface CoinView{
    var rootView: CoordinatorLayout?
    val viewContext: Context?
    fun setShimmerIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                /*var loadingView = it.findViewById<View>(R.id.loading)
                if (loadingView == null && mustShow) {

                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.shimmer_placeholder_layout, it, false)
                    it.addView(loadingView)

                }
                if (mustShow){
                    loadingView.visibility = View.VISIBLE
                    loadingView.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout).startShimmer()
                    loadingView.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout).visibility = View.VISIBLE
                }else{
                    if (rootView !=null){
                        loadingView.visibility = View.GONE
                        loadingView.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout).stopShimmer()
                        loadingView.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout).visibility = View.GONE
                    }
                }*/
               // loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE

            }

        }
    }
}

abstract class CoinViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
    }

}