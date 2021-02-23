package com.wc.learn.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

class ToastUtils {
    companion object {
        private var toast: Toast? = null

        @SuppressLint("ShowToast")
        @JvmStatic
        fun show(context: Context, text: String) {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            } else {
                toast?.setText(text)
                toast?.duration = Toast.LENGTH_SHORT
            }
            toast?.show()
        }
    }
}