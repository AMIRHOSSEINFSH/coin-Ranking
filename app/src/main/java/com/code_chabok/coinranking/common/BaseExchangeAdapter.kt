package com.code_chabok.coinranking.common


import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.data.model.dataClass.localModel.Exchange
import com.code_chabok.coinranking.databinding.ItemExchangeBinding
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence


class BaseExchangeAdapter constructor(
    private val onItemLongClickListener: (Exchange) -> Unit,
    private val onChangeDir: (Boolean, Int) -> Unit,
    private val onActivityProvider: () -> Activity
) : ListAdapter<Exchange, BaseExchangeAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<Exchange>() {

        override fun areItemsTheSame(
            oldItem: Exchange,
            newItem: Exchange
        ): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(
            oldItem: Exchange,
            newItem: Exchange
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    fun showBubble() {
        BubbleShowCaseSequence()
            .addShowCase(first)
            .addShowCase(second)
            .show()
    }

    private lateinit var first: BubbleShowCaseBuilder
    private lateinit var second: BubbleShowCaseBuilder
    private var tof = true
    var showBubble = true

    inner class MyViewHolder(val binding: ItemExchangeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var item: Exchange? = null
        var itemPosition: Int = -1

        init {
            //binding.constExpandable.implementSpringAnimationTrait()


            binding.constExpandable.setOnLongClickListener {

                item?.let { item ->
                    Log.d("Logging ...", ": item-> ${item} itemPosition -> ${itemPosition}")
                    //binding.cryptoNameTv.text = "Bit"
                    this.item?.isExpanded = !item.isExpanded
                    binding.expandableLayout.isVisible = item.isExpanded
                    notifyItemChanged(itemPosition)
                    true
                } ?: false
            }

            binding.constExpandable.setOnClickListener {
                onChangeDir(isDetail, itemPosition)
            }

        }

        fun bind(item: Exchange) {
            binding.constExpandable.implementSpringAnimationTrait()
            binding.exchangeListModel = item
            binding.expandableLayout.isVisible = item.isExpanded

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemExchangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.item = getItem(position)
        holder.itemPosition = position
        holder.bind(getItem(position))
    }

    fun scanList(): Boolean {
        var counter = 0
        currentList.forEach {
            if (it.isExpanded) {
                counter++
                if (counter > 1)
                    return@forEach
            }
        }
        return counter <= 1
    }


}