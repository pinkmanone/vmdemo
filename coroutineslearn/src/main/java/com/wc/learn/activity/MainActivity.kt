package com.wc.learn.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.wc.learn.R
import com.wc.learn.adapter.HomeAdapter
import com.wc.learn.base.BaseActivity
import com.wc.learn.databinding.ActivityMainBinding
import com.wc.learn.network.getNetworkService

class MainActivity : BaseActivity() {

    private var adapter:HomeAdapter?= null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        binding.btnGet.setOnClickListener {
            getData()
        }
        adapter = HomeAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)

        binding.rv.adapter = adapter
    }

    private fun getData() {
//        launchDataScope {
//            val result = getNetworkService().getHomeArticles(0)
//            println(result)
//            adapter?.refresh(result.data?.datas!!)
//        }
    }

    override fun showProgress(show: Boolean) {
        binding.progress.visibility = if (show) View.VISIBLE else View.GONE
    }
}