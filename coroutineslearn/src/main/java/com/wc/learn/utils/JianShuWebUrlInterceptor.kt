package com.wc.learn.utils

import android.net.Uri
import android.webkit.WebResourceResponse
import com.wc.learn.WApp
import com.wc.learn.network.WebHttpClient
import java.io.ByteArrayInputStream
import java.util.regex.Pattern

object JianShuWebUrlInterceptor {
    fun intercept(
        uri: Uri,
        userAgent: String?,
        reqHeaders: Map<String, String>?,
        reqMethod: String?
    ): WebResourceResponse? {
        val url = uri.toString();
        return if (url.startsWith("https://www.jianshu.com/p/")) {
            val str = WebHttpClient.request(uri.toString(), userAgent, reqHeaders, reqMethod)
            val s: String = if (str.isNullOrEmpty()) {
                ""
            } else {
                replaceCss(str)
            }
            WebResourceResponse("text/html", "utf-8", ByteArrayInputStream(s.toByteArray()))
        } else {
            null
        }
    }

    private val rex = "(<style data-vue-ssr-id=[\\s\\S]*?>)([\\s\\S]*]?)(<\\/style>)"
    private val bodyRex = "<body class=\"([\\ss\\S]*?)\""
    private fun darkBody(res: String): String {
        val pattern = Pattern.compile(bodyRex)
        val m = pattern.matcher(res)
        return if (m.find()) {
            val s = "<body class=\"reader-night-mode normal-size\""
            res.replace(bodyRex.toRegex(), s)
        } else res
    }

    private fun replaceCss(res: String): String {
        val pattern = Pattern.compile(rex)
        val m = pattern.matcher(res)
        return if (m.find()) {
            val css =
                WApp.application?.applicationContext?.resources?.assets?.open("css/jianshu.css")
                    .use { inputStream ->
                        inputStream?.let {
                            try {
                                val buffer = ByteArray(it.available())
                                it.read(buffer)
                                String(buffer)
                            } catch (e: Throwable) {
                                ""
                            }
                        }
                    }
            val sb = StringBuilder()
            sb.append(m.group(1))
            sb.append(css)
            sb.append(m.group(3))
            res.replace(rex.toRegex(), sb.toString())
        } else {
            res
        }
    }
}