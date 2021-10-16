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
import com.code_chabok.coinranking.data.model.dataClass.localModel.Bookmark
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.databinding.ItemCryptoBinding
import com.code_chabok.coinranking.domain.getListOfCoins
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
    private val onUpdateClickListener: ((String, Boolean, Int) -> Unit)? = null,
    private val onItemLongClickListener: suspend (CoinAndBookmark) -> LiveData<CoinDetail>,
    private val onChangeDir: (Boolean, Int) -> Unit

) : ListAdapter<CoinAndBookmark, BaseCoinAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<CoinAndBookmark>() {

        override fun areItemsTheSame(oldItem: CoinAndBookmark, newItem: CoinAndBookmark): Boolean {
            return oldItem.coin.uuid == newItem.coin.uuid
        }

        override fun areContentsTheSame(
            oldItem: CoinAndBookmark,
            newItem: CoinAndBookmark
        ): Boolean {
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
        var item: CoinAndBookmark? = null
        var itemPosition: Int = -1

        init {
            binding.constExpandable.implementSpringAnimationTrait()

            binding.cryptoBookmarkIv.setOnClickListener {
                onUpdateClickListener?.invoke(
                    item?.coin?.uuid!!,
                    item?.bookmark == null,
                    itemPosition
                )
                if (item?.bookmark == null) {
                    item?.bookmark = Bookmark(item?.coin?.uuid!!)
                    binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_fill
                    binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_fill)
                } else {
                    if (!isDetail){

                        currentList.removeAt(adapterPosition)
                    }
                    item?.bookmark = null
                    binding.cryptoBookmarkIv.tag = R.drawable.ic_bookmarks_empty
                    binding.cryptoBookmarkIv.setImageResource(R.drawable.ic_bookmarks_empty)
                }
            }


            binding.constExpandable.setOnLongClickListener {

                item?.let { item ->
                    Log.d("Logging ...", ": item-> ${item} itemPosition -> ${itemPosition}")
                    //binding.cryptoNameTv.text = "Bit"
                    this.item?.coin?.isExpanded = !item.coin.isExpanded
                    binding.expandableLayout.isVisible = item.coin.isExpanded
                    notifyItemChanged(itemPosition)
                    true
                } ?: false
            }

            binding.ClickContainer.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.cryptoIv to "iconTransition")
                onChangeDir(isDetail, itemPosition)
                com.code_chabok.coinranking.common.isDetail = true
            }

        }

        fun bind(item: CoinAndBookmark) {
//            this@BaseCoinAdapter.item = item
            binding.coin = item.coin
            binding.expandableLayout.isVisible = item.coin.isExpanded

            binding.cryptoBookmarkIv.also {
                if (item.bookmark != null) {
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
        Log.d("ExpandableTAG", getItem(position).coin.isExpanded.toString())
        holder.item = getItem(position)
        holder.itemPosition = position
        holder.bind(getItem(position))
    }

    fun scanList(): Boolean {
        var counter = 0
        currentList.forEach {
            if (it.coin.isExpanded) {
                counter++
                if (counter > 1)
                    return@forEach
            }
        }
        return counter <= 1
    }
}