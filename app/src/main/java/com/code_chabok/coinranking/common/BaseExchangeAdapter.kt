package com.code_chabok.coinranking.common

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.data.model.Exchange
import com.code_chabok.coinranking.databinding.ItemExchangeBinding
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence

class BaseExchangeAdapter constructor(
    private val onItemClickListener: (Exchange) -> Unit,

    ) : ListAdapter<Exchange, BaseExchangeAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<Exchange>() {

        override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {
    private lateinit var activity: Activity

    fun setActivity(activity: Activity) {
        this.activity = activity
    }
    fun showBubble(){
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
        fun bind(item: Exchange) {
            binding.constExpandable.implementSpringAnimationTrait()

            //binding.model = item
            //binding.callback = this

            if (adapterPosition == 1 && tof){
                item.isExpanded = true
                binding.exchangeDivider.visibility = View.GONE
                binding.expandableLayout.visibility = View.VISIBLE
                tof = false


                first = BubbleShowCaseBuilder(activity)
                    .title("You can watch more Details here!\n by Long Click")
                    .targetView(binding.constExpandable).showOnce("BUBBLE_SHOW_CASE_ID_0")

                second = BubbleShowCaseBuilder(activity)
                    .title("You can watch more Details here!\n by Long Click")
                    .targetView(binding.expandableLayout).showOnce("BUBBLE_SHOW_CASE_ID_1")

                if (showBubble){
                    showBubble()
                }

            }

            val isExpanded = item.isExpanded
            if (isExpanded){

                binding.constExpandable.setOnLongClickListener {
                    item.isExpanded = false
                    binding.exchangeDivider.visibility = View.GONE
                    binding.expandableLayout.visibility = View.GONE
                    notifyItemChanged(adapterPosition)
                    true
                }
            }
            else{

                binding.constExpandable.setOnLongClickListener {
                    if (scanList()){
                        item.isExpanded = true
                        binding.exchangeDivider.visibility = View.VISIBLE
                        binding.expandableLayout.visibility = View.VISIBLE
                        Log.i("TAGAAB", "bind: ${item.Id}")
                        notifyItemChanged(adapterPosition)
                    }

                    true
                }
            }
            binding.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

            binding.constExpandable.setOnClickListener {
                onItemClickListener(item)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemExchangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun scanList():Boolean{
        var counter = 0
        currentList.forEach {
            if (it.isExpanded){
                counter++
                if(counter>1)
                    return@forEach
            }
        }
        return counter <= 1
    }



}