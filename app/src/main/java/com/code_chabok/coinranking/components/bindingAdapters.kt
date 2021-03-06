package com.code_chabok.coinranking.components

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*


@BindingAdapter("app:formatPrice")
fun formatPrice(tv: TextView, number: Double?) {
    if (number == 0.0){
        tv.text = "0.0"
    }
    number?.apply {
        val str = NumberFormat.getCurrencyInstance(Locale("en", "US")).format(this.toDouble())
        tv.text = str
    }


}

@BindingAdapter("app:bindVolume")
fun formatVolume(tv: TextView, numbert: Double?) {
    if (numbert!=0.0)
    numbert?.apply {
        val split = this.toString().split(".")
        if (split[0].length >= 2)
        tv.text = "${split[0].substring(0, 2)}.${split[1].substring(0, 2)} billion"
        else
            tv.text = "${split[0]}.${split[1].substring(0, 2)} billion"
    }

}

@BindingAdapter("app:rateImpl")
fun implRate(tv: TextView, number: String?) {

    if (number?.length!=0 && number!=null) {
        if (number.substring(0, 1).equals("-")) {
            tv.text = "$number%"
            tv.setTextColor(Color.parseColor("#eb5e2c"))
        } else {
            tv.text = "+${number}%"
            tv.setTextColor(Color.parseColor("#2BCF31"))
        }
    }
    else{
        tv.text = "0.0"
    }
}
