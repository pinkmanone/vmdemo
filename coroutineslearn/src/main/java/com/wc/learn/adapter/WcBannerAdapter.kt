package com.wc.learn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wc.learn.R
import com.wc.learn.data.BannerData
import com.wc.learn.databinding.LayoutBannerPageBinding
import com.youth.banner.adapter.BannerAdapter

class WcBannerAdapter(datas: MutableList<BannerData>) : BannerAdapter<BannerData, WcBannerAdapter.BannerHolder>(datas) {


    class BannerHolder(val binding: LayoutBannerPageBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerHolder {
        val binding: LayoutBannerPageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.layout_banner_page, parent, false)

        return BannerHolder(binding)
    }

    override fun onBindView(holder: BannerHolder?, data: BannerData?, position: Int, size: Int) {
        holder?.binding?.banner = data
    }
}