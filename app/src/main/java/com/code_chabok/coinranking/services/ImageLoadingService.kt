package com.code_chabok.coinranking.services

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class ImageLoadingService {



}
@BindingAdapter("app:loadImg")
fun load(imageView: ImageView, imageUrl: String?) {
    //"https://cdn.coinranking.com/rk4RKHOuW/eth.svg"
    imageUrl?.replace(".svg",".png")
    imageView.load(imageUrl)
    //todo use for loading Image form remote by Library (ie:Glide or ...)
    //load(imageView,"https://cdn.coinranking.com/o3-8cvCHu/wbtc[1].png")
}