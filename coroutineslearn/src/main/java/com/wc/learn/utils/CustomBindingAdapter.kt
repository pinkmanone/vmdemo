package com.wc.learn.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wc.learn.adapter.HomeAdapter
import com.wc.learn.adapter.WcBannerAdapter
import com.wc.learn.data.BannerData
import com.youth.banner.Banner

object CustomBindingAdapter {
    @JvmStatic
    @BindingAdapter("imgUrl")
    fun setImgUrl(iv: ImageView?, url: String?) {
        Glide.with(iv!!).load(url).into(iv)
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(
        banner: Banner<BannerData?, WcBannerAdapter?>,
        adapter: WcBannerAdapter?
    ) {
        banner.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("articleAdapter")
    fun setAdapter(rv: RecyclerView, adapter: HomeAdapter) {
        rv.adapter = adapter
    }
}