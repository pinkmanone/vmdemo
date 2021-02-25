package com.wc.learn.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wc.learn.R
import com.wc.learn.adapter.HomeAdapter
import com.wc.learn.base.BaseVMFragment
import com.wc.learn.data.KnowledgeTag
import com.wc.learn.databinding.FragmentKnowledgeArticleBinding
import com.wc.learn.databinding.ListLoadMoreBinding
import com.wc.learn.vms.TagArticleViewModel

class KnowledgeArticleFragment :
    BaseVMFragment<FragmentKnowledgeArticleBinding, TagArticleViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_knowledge_article


    override fun initView() {
        super.initView()

        val data: KnowledgeTag = arguments?.get("data") as KnowledgeTag
        var page = 0

        val footer = LayoutInflater.from(activity).inflate(R.layout.list_load_more, null)
        footer.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val footerBinding: ListLoadMoreBinding? = DataBindingUtil.bind(footer)

        binding.refresh.setOnRefreshListener {
            viewModel.getData(0, data.id)
        }

        val adapter = HomeAdapter()
        adapter.addFootView(footerBinding?.root)

        binding.rvTree.layoutManager = LinearLayoutManager(requireActivity())
        binding.adapter = adapter

        viewModel.endLiveData.observe(requireActivity(), Observer {
            if (it) {
                footerBinding?.progress?.visibility = View.GONE
                footerBinding?.tvContent?.text = "没有更多数据了"
            } else {
                footerBinding?.progress?.visibility = View.VISIBLE
                footerBinding?.tvContent?.text = "加载中"
            }
        })

        viewModel.pageLiveData.observe(requireActivity(), Observer {
            page = it
        })

        viewModel.articleLiveData.observe(requireActivity(), Observer {
            if (page == 1) {
                adapter.refresh(it)
            } else {
                adapter.add(it)
            }
        })

        viewModel.getData(page, data.id)
    }

    companion object {
        @JvmStatic
        fun create(data: KnowledgeTag): KnowledgeArticleFragment {
            return KnowledgeArticleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("data", data)
                }
            }
        }
    }
}