package com.wc.learn.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.wc.learn.R
import com.wc.learn.adapter.MainPagerAdapter
import com.wc.learn.base.BaseActivity
import com.wc.learn.databinding.ActivityMainBinding
import com.wc.learn.fragment.HomeFragment
import com.wc.learn.fragment.KnowledgeSystemFragment
import com.wc.learn.fragment.MeFragment
import com.wc.learn.fragment.QAFragment
import com.wc.learn.utils.BottomNavigationViewPagerHelper

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val fragments = arrayListOf<Fragment>(
            HomeFragment(), QAFragment(), KnowledgeSystemFragment(), MeFragment()
        )

        binding.pager.adapter = MainPagerAdapter(this, fragments)

        BottomNavigationViewPagerHelper.setupWithViewPager(binding.nav, binding.pager)

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.toolbar.title = when (position) {
                    0 -> {
                        "首页"
                    }
                    1 -> {
                        "问答"
                    }
                    2 -> {
                        "体系"
                    }
                    3 -> {
                        "我的"
                    }
                    else -> {
                        ""
                    }
                }
            }
        })

    }
}