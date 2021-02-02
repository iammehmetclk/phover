package com.phover.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingUtility {
    @BindingAdapter("setImageUrl")
    @JvmStatic fun ImageView.setImageUrl(url: String) {
        Glide.with(this).load(url).centerCrop().into(this)
    }
}