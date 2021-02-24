package com.wc.learn.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wc.learn.R
import com.wc.learn.activity.ArticleActivity
import com.wc.learn.adapter.HomeAdapter
import com.wc.learn.base.BaseAdapter
import com.wc.learn.base.BaseVMFragment
import com.wc.learn.callback.RecyclerViewBottomListener
import com.wc.learn.data.ArticleData
import com.wc.learn.databinding.FragmentQABinding
import com.wc.learn.databinding.ListLoadMoreBinding
import com.wc.learn.vms.QAViewModel

class QAFragment : BaseVMFragment<FragmentQABinding, QAViewModel>() {

    private var adapter: HomeAdapter? = null
    private var page = 0
    private var footerBinding: ListLoadMoreBinding? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_q_a
    }


    override fun initView() {
        super.initView()
        val footer = LayoutInflater.from(activity).inflate(R.layout.list_load_more, null)
        footer.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        footerBinding = DataBindingUtil.bind(footer)

        adapter = HomeAdapter()
        adapter?.addFootView(footerBinding?.root)

        adapter?.setClickListener(object : BaseAdapter.OnItemClickListener<ArticleData> {
            override fun click(t: ArticleData?, position: Int) {
                t?.let {
                    ArticleActivity.start(requireActivity(), t.title, t.link)
                }
            }
        })

        binding.rv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.adapter = adapter
        binding.refresh.setOnRefreshListener {
            viewModel.getData(0)
        }

        binding.rv.addOnScrollListener(object : RecyclerViewBottomListener() {
            override fun onBottom() {
                viewModel.getData(page)
            }
        })

        viewModel.pageData.observe(this, Observer {
            page = it + 1
        })

        viewModel.articleData.observe(this, Observer {
            if (page == 1) adapter?.refresh(it)
            else adapter?.add(it)
            binding.refresh.isRefreshing = false
        })

    }

}