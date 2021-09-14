package com.code_chabok.coinranking.common

import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}


@Suppress("deprecation")
fun Activity.enableFullScreenMode() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

@Suppress("deprecation")
fun Activity.disableFullScreenMode() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
}