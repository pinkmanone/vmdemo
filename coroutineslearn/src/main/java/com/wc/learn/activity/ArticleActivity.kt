package com.wc.learn.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import com.wc.learn.R
import com.wc.learn.base.BaseActivity
import kotlinx.android.synthetic.main.activity_article.*


class ArticleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        initView()
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        web.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setSupportZoom(false)
            builtInZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
            setAppCacheEnabled(true)
            domStorageEnabled = true
            setGeolocationEnabled(true)
            setAppCacheMaxSize(Long.MAX_VALUE)
            pluginState = WebSettings.PluginState.ON_DEMAND
            cacheMode = WebSettings.LOAD_DEFAULT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString()
                url?.let {
                    val hit = view?.hitTestResult
                    //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                    if (TextUtils.isEmpty(hit?.extra) || hit?.type == 0) {
                        //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                        Log.e(
                            "重定向",
                            "重定向: " + hit?.type + " && EXTRA（）" + hit?.extra + "------"
                        )
                        Log.e(
                            "重定向",
                            "GetURL: " + view!!.url
                                .toString() + "\n" + "getOriginalUrl()" + view.originalUrl
                        )
                        Log.d("重定向", "URL: $url")
                    }

                    return if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                        view!!.loadUrl(url)
                        false //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                    } else { //加载的url是自定义协议地址
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                        true
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    val hit = view?.hitTestResult
                    //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                    if (TextUtils.isEmpty(hit?.extra) || hit?.type == 0) {
                        //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                        Log.e(
                            "重定向",
                            "重定向: " + hit?.type + " && EXTRA（）" + hit?.extra + "------"
                        )
                        Log.e(
                            "重定向",
                            "GetURL: " + view!!.url
                                .toString() + "\n" + "getOriginalUrl()" + view.originalUrl
                        )
                        Log.d("重定向", "URL: $url")
                    }

                    return if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                        view!!.loadUrl(url)
                        false //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                    } else { //加载的url是自定义协议地址
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                        true
                    }
                }
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
        web.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progress.progress = newProgress
            }
        }

        intent.getStringExtra("url")?.let {
            web.loadUrl(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            web.destroy()
        } catch (e: Exception) {
        }
    }

    companion object {
        @JvmStatic
        fun start(activity: Context, title: String, url: String) {
            Intent(activity, ArticleActivity::class.java).apply {
                putExtra("url", url)
                putExtra("title", title)
            }.also {
                activity.startActivity(it)
            }
        }
    }
}