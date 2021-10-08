package com.code_chabok.coinranking.common

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
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
    private val onUpdateClickListener: (String, Boolean,Int) -> Unit,
    private val onItemClickListener: suspend (CoinListModel) -> LiveData<CoinDetail>

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
        fun bind(item: CoinListModel) {
            binding.constExpandable.implementSpringAnimationTrait()

            binding.coinListModel = item

            binding.cryptoBookmarkIv.also {
                if (item.isBookmarked == true) {
                    it.setTag(R.drawable.ic_bookmarks_fill)
                    it.setImageResource(R.drawable.ic_bookmarks_fill)
                } else {
                    it.setTag(R.drawable.ic_bookmarks_empty)
                    it.setImageResource(R.drawable.ic_bookmarks_empty)
                }
                //DrawableCompat.setTint(it.getDrawable(), ContextCompat.getColor(activity, R.color.black_200));
            }


            binding.cryptoBookmarkIv.setOnClickListener {
                onUpdateClickListener(item.uuid, !item.isBookmarked!!,adapterPosition)
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

            /*val dialog = AlertDialog.Builder(itemView.context).create()
            *//*dialog.setTitle("Hello")
            dialog.setMessage("This is Sample")*//*
            val view = LayoutInflater.from(itemView.context)
                .inflate(R.layout.custom_prev_dialog, null, false)
            dialog.setView(view)*/


            /* binding.cryptoIv.setOnTouchListener OnTouchListener@{ view, motionEvent ->
                 var x = motionEvent.x
                 var y = motionEvent.y
                 when (motionEvent.action) {
                     MotionEvent.ACTION_DOWN -> { // RELEASED
                         dialog.show()
                         //oast.makeText(this.context, "Action Up", Toast.LENGTH_SHORT).show()
                         //Log.i("TAGAA", "onViewCreated: ")
                         Toast.makeText(view.context, "Down", Toast.LENGTH_SHORT).show()

                         return@OnTouchListener true

                     }
                     MotionEvent.ACTION_UP -> {
                         dialog.dismiss()

                         Toast.makeText(view.context, "Up", Toast.LENGTH_SHORT).show()
                         return@OnTouchListener true
                     } // if you want to handle the touch event

                     MotionEvent.ACTION_MOVE -> {
                         if (y > view.context.resources.displayMetrics.heightPixels*//*view.pivotY*//* *//*+ 200*//* || y < view.context.resources.displayMetrics.heightPixels*//* - 200*//*)
                            //view.pivotY = y
                                Toast.makeText(view.context, "reached", Toast.LENGTH_SHORT).show()
                                Log.i("TAGAAA", "Move $y form ${view.pivotY}")
                            //Toast.makeText(v.context, "Move $y", Toast.LENGTH_SHORT).show()
                            return@OnTouchListener true
                        }
                    }
                    false
                }*/


            var isExpanded = item.isExpanded
            if (isExpanded) {

                (activity as MainActivity).lifecycleScope.launchWhenResumed {
                    onItemClickListener(item).observe(activity as MainActivity) { coinDetail ->
                        binding.coinDetailModel = coinDetail
                        //Log.i("AAAAAA", "bind: ${coinDetail.btcPrice}")
                    }
                }
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
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
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