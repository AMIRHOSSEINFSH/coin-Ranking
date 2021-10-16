package com.code_chabok.coinranking.common

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BaseCoinAdapter constructor(
    private val onUpdateClickListener: ((String, Boolean, Int) -> Unit)?= null,
    private val onItemLongClickListener: suspend (CoinListModel) -> LiveData<CoinDetail>,
    private val onChangeDir: (Boolean,Int) -> Unit

) : ListAdapter<CoinListModel, BaseCoinAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<CoinListModel>() {

        override fun areItemsTheSame(oldItem: CoinListModel, newItem: CoinListModel): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: CoinListModel, newItem: CoinListModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
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
        var item: CoinListModel? = null
        var itemPosition: Int = -1

        init {
            binding.constExpandable.implementSpringAnimationTrait()

            binding.cryptoBookmarkIv.setOnClickListener {
                item?.let { item ->
                    onUpdateClickListener?.invoke(item.uuid, !item.isBookmarked, adapterPosition)
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
            }


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

            binding.ClickContainer.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.cryptoIv to "iconTransition")
                onChangeDir(isDetail,itemPosition)
                com.code_chabok.coinranking.common.isDetail = true
            }

        }

        fun bind(item: CoinListModel) {
//            this@BaseCoinAdapter.item = item
            binding.coinListModel = item
            binding.expandableLayout.isVisible = item.isExpanded

            binding.cryptoBookmarkIv.also {
                if (item.isBookmarked) {
                    it.tag = R.drawable.ic_bookmarks_fill
                    it.setImageResource(R.drawable.ic_bookmarks_fill)
                } else {
                    it.tag = R.drawable.ic_bookmarks_empty
                    it.setImageResource(R.drawable.ic_bookmarks_empty)
                }
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val myViewHolder = MyViewHolder(binding)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("ExpandableTAG", getItem(position).isExpanded.toString())
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