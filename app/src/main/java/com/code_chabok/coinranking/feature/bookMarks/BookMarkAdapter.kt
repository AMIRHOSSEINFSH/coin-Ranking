package com.code_chabok.coinranking.feature.bookMarks

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.formatPrice
import com.code_chabok.coinranking.common.implementSpringAnimationTrait
import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.databinding.ItemCryptoBinding
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseListener
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence
import kotlinx.coroutines.delay
import javax.inject.Inject


class BookMarkAdapter @Inject constructor(
    /*private val onItemClickListener: (Crypto) -> Unit,
    private val onLockRec: (Boolean) -> Unit,*/
    //private val activity: Activity
) : ListAdapter<Crypto, BookMarkAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<Crypto>() {

        override fun areItemsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {
    private lateinit var activity: Activity
    fun setActivity(activity: Activity) {
        this.activity = activity
    }
    var tof = true
    inner class MyViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Crypto) {
            binding.constExpandable.implementSpringAnimationTrait()

            binding.model = item
            if (adapterPosition == 1 && tof){
                item.isExpanded = true
                binding.cryptoDivider.visibility = View.VISIBLE
                binding.expandableLayout.visibility = View.VISIBLE
                tof = false

                /*val first = BubbleShowCaseBuilder(activity)
                    .title("You can watch more Details here!\n by Long Click")
                    .targetView(binding.constExpandable)

                val second = BubbleShowCaseBuilder(activity)
                    .title("You can watch more Details here!\n by Long Click")
                    .targetView(binding.expandableLayout)

            BubbleShowCaseSequence()
                .addShowCase(first)
                .addShowCase(second)
                .show()*/
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

            val isExpanded = item.isExpanded
            if (isExpanded){

                binding.constExpandable.setOnLongClickListener {
                    item.isExpanded = false
                    binding.cryptoDivider.visibility = View.GONE
                    binding.expandableLayout.visibility = View.GONE
                    notifyItemChanged(adapterPosition)
                    true
                }
            }
            else{

                binding.constExpandable.setOnLongClickListener {
                    if (scanList()){
                        item.isExpanded = true
                        binding.cryptoDivider.visibility = View.VISIBLE
                        binding.expandableLayout.visibility = View.VISIBLE
                        Log.i("TAGAAB", "bind: ${item.Id}")
                        notifyItemChanged(adapterPosition)
                    }

                    true
                }
            }
            binding.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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