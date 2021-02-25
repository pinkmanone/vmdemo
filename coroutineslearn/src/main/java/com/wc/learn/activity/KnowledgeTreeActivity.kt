package com.wc.learn.activity

import android.content.Context
import android.content.Intent
import com.google.android.material.tabs.TabLayoutMediator
import com.wc.learn.R
import com.wc.learn.adapter.KnowledgeTreePagerAdapter
import com.wc.learn.base.BaseBindingActivity
import com.wc.learn.data.KnowledgeTag
import com.wc.learn.databinding.ActivityKnowledgeTreeBinding

class KnowledgeTreeActivity : BaseBindingActivity<ActivityKnowledgeTreeBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context, data: KnowledgeTag, position: Int) {
            Intent(context, KnowledgeTreeActivity::class.java).apply {
                putExtra("data", data)
                putExtra("pos", position)
            }.also {
                context.startActivity(it)
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_knowledge_tree

    override fun initView() {
        super.initView()
        val tagData = intent.getParcelableExtra<KnowledgeTag>("data")
        val position = intent.getIntExtra("pos", 0)
        if (tagData == null) {
            finish()
        }
        binding.tag = tagData

        binding.pager.adapter = tagData?.children?.let {
            println("pager data  ===== $it")
            KnowledgeTreePagerAdapter(this, it)
        }

        TabLayoutMediator(binding.tab, binding.pager) { tab, pos ->
//            println("tab bind   position = $pos  =====  title = ${tagData!!.children[pos].name}")
//            tab.text = tagData!!.children[pos].name
            when (pos) {
                0 -> {
                    tab.text = "0"
                }
                else -> tab.text = pos.toString()
            }
        }

        binding.pager.currentItem = position

    }
}