package com.code_chabok.coinranking.services

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import android.R

import android.graphics.drawable.PictureDrawable
import android.util.Log

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener


class ImageLoadingService {



}
@BindingAdapter("app:loadImg")
fun load(imageView: ImageView, imageUrl: String?) {

    imageUrl?.let {url->
        GlideToVectorYou.init()
            .with(imageView.context)
            .withListener(object : GlideToVectorYouListener{
                override fun onLoadFailed() {

                    Log.d("TAG", "onLoadFailed: ")
                }

                override fun onResourceReady() {
                    Log.d("TAG", "onResourceReady: ")
                }

            })
            .load(Uri.parse(url),imageView)
    }




    //val uri = Uri.parse(imageUrl)

    //"https://cdn.coinranking.com/rk4RKHOuW/eth.svg"

    //todo use for loading Image form remote by Library (ie:Glide or ...)
    //load(imageView,"https://cdn.coinranking.com/o3-8cvCHu/wbtc[1].png")
}