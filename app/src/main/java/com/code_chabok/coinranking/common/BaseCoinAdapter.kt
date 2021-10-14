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
    private val onUpdateClickListener: (String, Boolean, Int) -> Unit,
    //private val onItemLongClickListener: suspend (CoinListModel) -> LiveData<CoinDetail>,
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
    private lateinit var item: CoinListModel
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
                    onUpdateClickListener(item.uuid, !item.isBookmarked, adapterPosition)
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

                    this.item?.isExpanded = !item.isExpanded
                    binding.expandableLayout.isVisible = item.isExpanded
                    notifyItemChanged(itemPosition)
//                    if (item.isExpanded) {
//                        item.isExpanded = false
//                        binding.cryptoDivider.visibility = View.GONE
//                        binding.expandableLayout.visibility = View.GONE
////                        notifyItemChanged(itemPosition)
//                    }
//                    else {
//                        item.isExpanded = true
//                        binding.cryptoDivider.visibility = View.VISIBLE
//                        binding.expandableLayout.visibility = View.VISIBLE
////                        notifyItemChanged(itemPosition)
//                        Log.i("TAG", ": ${itemPosition}")
//                    }
//                    binding.expandableLayout.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
                    true
                } ?: false
            }

            binding.ClickContainer.setOnClickListener {
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

/*            if (adapterPosition == 0 && tof) {
                */
        /*item.isExpanded = true*//*
                */
        /*binding.cryptoDivider.visibility = View.GONE
                binding.expandableLayout.visibility = View.VISIBLE
                tof = false*/
        /*
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

            }*/


            //binding.expandableLayout.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
            /*if (item.isExpanded){
                binding.expandableLayout.setOnLongClickListener {
                    item.isExpanded = false
                    binding.cryptoDivider.visibility = View.GONE
                    binding.expandableLayout.visibility = View.GONE
                    notifyItemChanged(layoutPosition)
                    true
                }
            }else{
                binding.expandableLayout.setOnLongClickListener {
                    item.isExpanded = true
                    binding.cryptoDivider.visibility = View.VISIBLE
                    binding.expandableLayout.visibility = View.VISIBLE
                    notifyItemChanged(layoutPosition)
                    true
                }
            }
            binding.expandableLayout.visibility = if (item.isExpanded) View.VISIBLE else View.GONE*/
            /*var isExpanded = item.isExpanded
            if (isExpanded) {

                *//*(activity as MainActivity).lifecycleScope.launchWhenResumed {
                    onItemLongClickListener(item).observe(activity as MainActivity) { coinDetail ->
                        binding.coinDetailModel = coinDetail
                        //Log.i("AAAAAA", "bind: ${coinDetail.btcPrice}")
                    }
                }*/
        /*
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
            binding.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

            binding.ClickContainer.setOnClickListener {
                val bundle = Bundle().apply {
                    //putParcelable("item", item)
                    putString("uuid", item.uuid)
                }
                if (!isDetail)
                    itemView.findNavController().navigate(R.id.home_book_same, bundle)
                else
                    itemView.findNavController().navigate(R.id.action_same_to_same, bundle)
                //onItemClickListener(item)
                com.code_chabok.coinranking.common.isDetail = true
            }*/

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val myViewHolder = MyViewHolder(binding)

        /*if (myViewHolder.layoutPosition!=-1){
            //var item = currentList[myViewHolder.layoutPosition]
            if (item.isExpanded){

                */
        /*binding.constExpandable.setOnTouchListener { view, motionEvent ->

                    when(motionEvent.action){
                        MotionEvent.ACTION_UP ->{
                            binding.cryptoDivider.visibility = View.GONE
                            binding.expandableLayout.visibility = View.GONE
                            notifyItemChanged(myViewHolder.layoutPosition)
                        }
                    }

                    false
                }*//*
                *//*binding.cryptoDivider.visibility = View.GONE
                binding.expandableLayout.visibility = View.GONE*//*
            }
            else{
                binding.constExpandable.setOnLongClickListener {
                    item.isExpanded = true
                    binding.cryptoDivider.visibility = View.VISIBLE
                    binding.expandableLayout.visibility = View.VISIBLE
                    notifyItemChanged(myViewHolder.layoutPosition)
                    true
                }
                */
        /*binding.cryptoDivider.visibility = View.VISIBLE
                binding.expandableLayout.visibility = View.VISIBLE*//*
            }
            binding.expandableLayout.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
        }*/

        /*binding.ClickContainer.setOnClickListener {
            onChangeDir(isDetail,myViewHolder.layoutPosition)
            com.code_chabok.coinranking.common.isDetail = true
        }*/

        /*binding.cryptoBookmarkIv.setOnClickListener {
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

        }*/
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