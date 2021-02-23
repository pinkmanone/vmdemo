package com.wc.learn.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import com.wc.learn.R
import com.wc.learn.base.BaseAdapter
import com.wc.learn.data.ArticleData
import com.wc.learn.databinding.ItemArticleBinding

class HomeAdapter : BaseAdapter<ArticleData>(R.layout.item_article) {
    override fun convert(root: View, t: ArticleData?, position: Int, listener: OnItemClickListener<ArticleData>?) {
        val itemBinding: ItemArticleBinding? = DataBindingUtil.bind(root)
        itemBinding?.article = t
    }
}