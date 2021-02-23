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
import com.wc.learn.adapter.WcBannerAdapter
import com.wc.learn.base.BaseVMFragment
import com.wc.learn.callback.RecyclerViewBottomListener
import com.wc.learn.data.BannerData
import com.wc.learn.databinding.BannerLayoutBinding
import com.wc.learn.databinding.FragmentHomeBinding
import com.wc.learn.databinding.ListLoadMoreBinding
import com.wc.learn.vms.HomeViewModel

class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {
    private var bannerBinding: BannerLayoutBinding? = null
    private var footerBinding: ListLoadMoreBinding? = null

    private var bannerAdapter: WcBannerAdapter? = null
    private var banners = arrayListOf<BannerData>()

    private var adapter: HomeAdapter? = null

    private var page = 0

//    private val homeViewModel: HomeViewModel by lazy {
//        ViewModelProvider(this).get(HomeViewModel::class.java)
//    }

    lateinit var homeViewModel: HomeViewModel


    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        homeViewModel = viewModel
        getBinding()
        bannerAdapter = WcBannerAdapter(banners)
        bannerBinding?.adapter = bannerAdapter

        binding.rv.layoutManager = LinearLayoutManager(activity)
        adapter = HomeAdapter()

        adapter?.addHeaderView(bannerBinding?.root)
        adapter?.addFootView(footerBinding?.root)
        binding.rv.adapter = adapter

        binding.rv.addOnScrollListener(object : RecyclerViewBottomListener() {
            override fun onBottom() {
//                getData()
                homeViewModel.getData(page)
            }
        })
        binding.rv.itemAnimator?.changeDuration = 0

//        homeViewModel.getData(0)
//        homeViewModel.getBanner()

        binding.refresh.setOnRefreshListener {
            homeViewModel.getData(0)
            homeViewModel.getBanner()
        }

        homeViewModel.refreshType.observe(requireActivity(), Observer {
            page = it + 1
            println("live data  refreshType == $it")
        })

        homeViewModel.articleData.observe(requireActivity(), Observer {
            println("live data  data observer page == $page")
            if (page == 1) adapter?.refresh(it)
            else adapter?.add(it)
            binding.refresh.isRefreshing = false
        })

        homeViewModel.bannerData.observe(requireActivity(), Observer {
            banners.clear()
            banners.addAll(it)
            bannerAdapter?.notifyDataSetChanged()
        })
    }

    override fun showProgress(show: Boolean) {
        super.showProgress(show)
        if (!show) {
            binding.refresh.isRefreshing = false
        }
    }


    private fun getBinding() {
//        bannerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.banner_layout, null, false)
        val header = LayoutInflater.from(activity).inflate(R.layout.banner_layout, null)
        header.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        bannerBinding = DataBindingUtil.bind(header)

        val footer = LayoutInflater.from(activity).inflate(R.layout.list_load_more, null)
        footer.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        footerBinding = DataBindingUtil.bind(footer)
    }


}