package com.wc.learn.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wc.learn.R
import com.wc.learn.adapter.TreeAdapter
import com.wc.learn.base.BaseVMFragment
import com.wc.learn.databinding.FragmentKnowledgeTreeBinding
import com.wc.learn.vms.KnowledgeTreeViewModel

class KnowledgeTreeFragment :
    BaseVMFragment<FragmentKnowledgeTreeBinding, KnowledgeTreeViewModel>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_knowledge_tree
    }

    override fun initView() {
        super.initView()
        binding.rvTree.layoutManager = LinearLayoutManager(activity)
        val adapter = TreeAdapter()
        binding.rvTree.adapter = adapter

        binding.refresh.setOnRefreshListener {
            viewModel.getData()
        }

        viewModel.tagLiveData.observe(this, Observer {
            adapter.add(it)
            binding.refresh.isRefreshing = false
        })
    }
}