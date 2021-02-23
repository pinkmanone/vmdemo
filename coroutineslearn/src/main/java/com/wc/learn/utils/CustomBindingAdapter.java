package com.wc.learn.utils;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.wc.learn.adapter.WcBannerAdapter;
import com.wc.learn.data.BannerData;
import com.youth.banner.Banner;

public class CustomBindingAdapter {

    @BindingAdapter("imgUrl")
    public static void setImgUrl(ImageView iv, String url) {
        Glide.with(iv).load(url).into(iv);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(Banner<BannerData, WcBannerAdapter> banner, WcBannerAdapter adapter) {
        banner.setAdapter(adapter);
    }
}
