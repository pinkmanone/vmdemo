package com.wc.learn.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wc.learn.R
import com.wc.learn.base.BaseFragment
import com.wc.learn.databinding.FragmentKnowledgeSystemBinding

class KnowledgeSystemFragment : BaseFragment() {

    lateinit var binding: FragmentKnowledgeSystemBinding

    override fun getLayoutRes(): Int {
        return R.layout.fragment_knowledge_system
    }

    override fun useDataBinding(): Boolean {
        return true
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    override fun initView() {
        super.initView()

        binding.pagerKnowledge.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> KnowledgeTreeFragment()
                    else -> KnowledgeNavFragment()
                }
            }
        }
        TabLayoutMediator(binding.tab, binding.pagerKnowledge) { tab, position ->
            when (position) {
                0 -> tab.text = "体系"
                else -> tab.text = "导航"
            }
        }.attach()
    }

}