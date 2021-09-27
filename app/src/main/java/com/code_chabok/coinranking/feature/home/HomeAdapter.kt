package com.code_chabok.coinranking.feature.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.data.model.Crypto

class HomeAdapter(val context: Context, private val dataSet: List<Crypto>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.cryptoNameTv)
        val prev: TextView = view.findViewById(R.id.cryptoAbbrTv)
        val price: TextView = view.findViewById(R.id.cryptoPriceTv)
        val changedRate: TextView = view.findViewById(R.id.cryptoPriceChangeTv)
        val isBookmark: ImageView = view.findViewById(R.id.cryptoBookmarkIv)
        val imgUrl: ImageView = view.findViewById(R.id.cryptoIv)


    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_crypto, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = dataSet[position].name
        viewHolder.prev.text = dataSet[position].prev
        viewHolder.price.text = dataSet[position].price
        viewHolder.changedRate.text = dataSet[position].changedRate
        
    }

    override fun getItemCount() = dataSet.size

}