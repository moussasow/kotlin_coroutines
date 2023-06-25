package com.mas.kotlincourotines.ui

import android.view.View

fun View.setDebouncedClickListener(
    timeInterval: Int = 2000, // milliseconds
    listener: (view: View?) -> Unit
) {
    var lastClickTime: Long = 0
    val clickListener = View.OnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > timeInterval) {
            lastClickTime = currentTime
            listener(it)
        }
    }
    this.setOnClickListener(clickListener)
}