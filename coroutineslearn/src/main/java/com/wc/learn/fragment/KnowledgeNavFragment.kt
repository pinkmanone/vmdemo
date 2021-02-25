package com.wc.learn.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wc.learn.R
import com.wc.learn.activity.ArticleActivity
import com.wc.learn.adapter.TreeNavAdapter
import com.wc.learn.base.BaseAdapter
import com.wc.learn.base.BaseVMFragment
import com.wc.learn.data.NavData
import com.wc.learn.databinding.FragmentKnowledgeNaviBinding
import com.wc.learn.vms.KnowledgeNavViewModel

class KnowledgeNavFragment : BaseVMFragment<FragmentKnowledgeNaviBinding, KnowledgeNavViewModel>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_knowledge_navi
    }

    override fun initView() {
        super.initView()
        binding.rvNav.layoutManager = LinearLayoutManager(activity)
        val adapter = TreeNavAdapter()
        binding.rvNav.adapter = adapter

        adapter.setClickListener(object : BaseAdapter.OnItemClickListener<NavData> {
            override fun click(t: NavData?, position: Int) {
                t?.let {
                    ArticleActivity.start(
                        requireActivity(),
                        t.articles[position].title,
                        t.articles[position].link
                    )
                }

            }
        })

        binding.refresh.setOnRefreshListener {
            viewModel.getData()
        }

        viewModel.tagLiveData.observe(this, Observer {
            adapter.add(it)
            binding.refresh.isRefreshing = false
        })
    }
}