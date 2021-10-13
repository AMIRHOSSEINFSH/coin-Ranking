package com.code_chabok.coinranking.common

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.databinding.ItemCryptoBinding
import com.code_chabok.coinranking.feature.home.MainActivity
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseListener
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence

class BaseCoinAdapter constructor(
    private val onUpdateClickListener: (String, Boolean, Int) -> Unit,
    private val onItemLongClickListener: suspend (CoinListModel) -> LiveData<CoinDetail>,
    private val onChangeDir: (Boolean,Int) -> Unit

) : ListAdapter<CoinListModel, BaseCoinAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<CoinListModel>() {

        override fun areItemsTheSame(oldItem: CoinListModel, newItem: CoinListModel): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: CoinListModel, newItem: CoinListModel): Boolean {
            return oldItem.uuid == newItem.uuid
        }
    }

) {
    private lateinit var activity: Activity
    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    private var isDetail: Boolean = false
    fun setIsDetail(isDetail: Boolean) {
        this.isDetail = isDetail
    }

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

    inner class MyViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoinListModel) {
            binding.constExpandable.implementSpringAnimationTrait()

            binding.coinListModel = item

            binding.cryptoBookmarkIv.also {
                if (item.isBookmarked == true) {
                    it.tag = R.drawable.ic_bookmarks_fill
                    it.setImageResource(R.drawable.ic_bookmarks_fill)
                } else {
                    it.tag = R.drawable.ic_bookmarks_empty
                    it.setImageResource(R.drawable.ic_bookmarks_empty)
                }
            }

            /*binding.cryptoBookmarkIv.setOnClickListener {
                onUpdateClickListener(item.uuid, !item.isBookmarked!!, adapterPosition)
                if (it.tag == R.drawable.ic_bookmarks_fill) {
                    item.isBookmarked = false
                    binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_empty
                    binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_empty)
                } else if (it.tag == R.drawable.ic_bookmarks_empty) {
                    item.isBookmarked = true
                    binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_fill
                    binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_fill)
                }

            }*/

            if (adapterPosition == 0 && tof) {
                /*item.isExpanded = true*/
                /*binding.cryptoDivider.visibility = View.GONE
                binding.expandableLayout.visibility = View.VISIBLE
                tof = false*/
                first = BubbleShowCaseBuilder(activity)
                    .title("You can watch more Details here!\n by Long Click")
                    .targetView(binding.constExpandable).showOnce("BUBBLE_SHOW_CASE_ID_0")

                second = BubbleShowCaseBuilder(activity)
                    .title("You can watch more Details here!\n by Long Click")
                    .targetView(binding.expandableLayout).showOnce("BUBBLE_SHOW_CASE_ID_1")

                if (showBubble) {
                    // showBubble()
                }

                first.listener(object : BubbleShowCaseListener {
                    override fun onBackgroundDimClick(bubbleShowCase: BubbleShowCase) {

                    }

                    override fun onBubbleClick(bubbleShowCase: BubbleShowCase) {

                    }

                    override fun onCloseActionImageClick(bubbleShowCase: BubbleShowCase) {

                    }

                    override fun onTargetClick(bubbleShowCase: BubbleShowCase) {
                        item.isExpanded = true
                        binding.cryptoDivider.visibility = View.GONE
                        binding.expandableLayout.visibility = View.VISIBLE
                        tof = false
                    }

                })

            }

            /*(activity as MainActivity).lifecycleScope.launchWhenResumed {
                onItemLongClickListener(item).observe(activity as MainActivity) { coinDetail ->
                    binding.coinDetailModel = coinDetail
                    //Log.i("AAAAAA", "bind: ${coinDetail.btcPrice}")
                }
            }

            var isExpanded = item.isExpanded
            if (isExpanded) {

                binding.constExpandable.setOnLongClickListener {
                    item.isExpanded = false
                    binding.cryptoDivider.visibility = View.GONE
                    binding.expandableLayout.visibility = View.GONE
                    notifyItemChanged(adapterPosition)
                    true
                }
                //item.isExpanded = false
            } else {

                binding.constExpandable.setOnLongClickListener {

                    if (scanList()) {
                        item.isExpanded = true
                        binding.cryptoDivider.visibility = View.VISIBLE
                        binding.expandableLayout.visibility = View.VISIBLE
                        Log.i("TAGAAB", "bind: ${item.uuid}")
                        notifyItemChanged(adapterPosition)
                    }

                    true
                }
            }
            binding.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE*/

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val myViewHolder = MyViewHolder(binding)

        binding.ClickContainer.setOnClickListener {
            onChangeDir(isDetail,myViewHolder.layoutPosition)
            com.code_chabok.coinranking.common.isDetail = true
        }

        binding.cryptoBookmarkIv.setOnClickListener {
            val item = currentList[myViewHolder.layoutPosition]
            onUpdateClickListener(item.uuid, !item.isBookmarked, myViewHolder.layoutPosition)
            if (it.tag == R.drawable.ic_bookmarks_fill) {
                item.isBookmarked = false
                binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_empty
                binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_empty)
            } else if (it.tag == R.drawable.ic_bookmarks_empty) {
                item.isBookmarked = true
                binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_fill
                binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_fill)
            }

        }
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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
