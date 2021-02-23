package com.wc.learn.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class Holder : RecyclerView.ViewHolder {
    constructor(itemView: View) : super(itemView) {}
    constructor(parent: ViewGroup, @LayoutRes resId: Int) : super(LayoutInflater.from(parent.context).inflate(resId, parent, false)) {}
}