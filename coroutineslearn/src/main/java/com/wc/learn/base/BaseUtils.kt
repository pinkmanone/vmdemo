package com.wc.learn.base

import android.content.Context
import com.wc.learn.utils.ToastUtils


//此处可定义扩展函数
fun Context.showToast(text: String) {
    ToastUtils.show(this, text)
}

