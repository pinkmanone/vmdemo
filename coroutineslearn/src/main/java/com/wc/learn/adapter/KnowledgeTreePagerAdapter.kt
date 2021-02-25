package com.wc.learn.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wc.learn.data.KnowledgeTag
import com.wc.learn.fragment.KnowledgeArticleFragment

class KnowledgeTreePagerAdapter(activity: FragmentActivity, val datas: List<KnowledgeTag>) :
    FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return datas.size
    }

    override fun createFragment(position: Int): Fragment {
        println("create fragment with  ${datas[position]}")
        return KnowledgeArticleFragment.create(datas[position])
    }
}