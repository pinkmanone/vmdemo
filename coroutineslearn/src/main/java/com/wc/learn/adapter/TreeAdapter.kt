package com.wc.learn.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.wc.learn.R
import com.wc.learn.base.BaseAdapter
import com.wc.learn.data.KnowledgeTag
import java.util.*

class TreeAdapter : BaseAdapter<KnowledgeTag>(R.layout.item_tree) {
    private val mFlexItemTextViewCaches: Queue<TextView> = LinkedList()
    private var mInflater: LayoutInflater? = null
    override fun convert(
        root: View,
        t: KnowledgeTag?,
        position: Int,
        listener: OnItemClickListener<KnowledgeTag>?
    ) {
        val textView = root.findViewById<TextView>(R.id.tv)
        val fbl = root.findViewById<FlexboxLayout>(R.id.fbl)
        t?.let {
            textView.text = t.name
            for (i in t.children.indices) {
                val child = t.children[i]
                val tv = createOrGetCacheFlexItemTextView(fbl)
                tv?.text = child.name
                tv?.setOnClickListener {
                    listener?.click(t, i)
                }
                fbl?.addView(tv)
            }
        }

        root.setOnClickListener {
            listener?.click(t!!, 0)
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