package com.code_chabok.coinranking.components

import android.view.View

class zoomOutTransformer: pageTransformer() {

    override fun onTransform(view: View?, position: Float) {
        val scale = if (position < 0) position + 1f else Math.abs(1f - position)
        view!!.scaleX = scale
        view.scaleY = scale
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
    }
}