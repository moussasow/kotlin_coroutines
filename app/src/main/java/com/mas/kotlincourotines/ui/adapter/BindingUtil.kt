package com.mas.kotlincourotines.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    if (url != null) {
        Picasso.get().load(url).into(imageView)
    }
}