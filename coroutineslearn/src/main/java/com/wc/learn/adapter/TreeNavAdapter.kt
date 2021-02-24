package com.wc.learn.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexboxLayout
import com.wc.learn.R
import com.wc.learn.base.BaseAdapter
import com.wc.learn.data.KnowledgeTag
import com.wc.learn.data.NavData
import com.wc.learn.databinding.ItemTreeBinding
import java.util.*

class TreeNavAdapter : BaseAdapter<NavData>(R.layout.item_tree) {
    private val mFlexItemTextViewCaches: Queue<TextView> = LinkedList()
    private var mInflater: LayoutInflater? = null
    override fun convert(
        root: View,
        t: NavData?,
        position: Int,
        listener: OnItemClickListener<NavData>?
    ) {
        val textView = root.findViewById<TextView>(R.id.tv)
        val fbl = root.findViewById<FlexboxLayout>(R.id.fbl)
        t?.let {
            textView.text = t.name
            for (i in t.articles.indices) {
                val child = t.articles[i]
                val tv = createOrGetCacheFlexItemTextView(fbl)
                tv?.text = child.title
                tv?.setOnClickListener {
                    listener?.click(t, i)
                }
                fbl?.addView(tv)
            }
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        val fbl: FlexboxLayout = holder.itemView.findViewById(R.id.fbl)
        for (i in 0 until fbl.childCount) {
            mFlexItemTextViewCaches.offer(fbl.getChildAt(i) as TextView)
        }
        fbl.removeAllViews()
    }

    private fun createOrGetCacheFlexItemTextView(fbl: FlexboxLayout?): TextView? {
        val tv = mFlexItemTextViewCaches.poll()
        return tv ?: createFlexItemTextView(fbl)
    }

    private fun createFlexItemTextView(fbl: FlexboxLayout?): TextView? {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl?.context)
        }
        return mInflater?.inflate(R.layout.layout_tree_item, fbl, false) as TextView
    }

}