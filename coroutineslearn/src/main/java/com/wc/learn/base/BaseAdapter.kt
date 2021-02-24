package com.wc.learn.base

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 列表适配器基类
 */

abstract class BaseAdapter<T>(@LayoutRes val id: Int) :
    RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {

    private val BASE_ITEM_TYPE_HEADER = 100000
    private val BASE_ITEM_TYPE_FOOTER = 200000
    private val ITEM_TYPE_EMPTY = Int.MAX_VALUE - 1


    private var datas: ArrayList<T> = arrayListOf()
    private var clickListener: OnItemClickListener<T>? = null

    private var headers: SparseArray<View> = SparseArray()
    private var footers: SparseArray<View> = SparseArray()

    private val mEmptyView: View? = null
    private val mEmptyLayoutId = 0

    fun refresh(data: List<T>) {
        val count = datas.size
        datas.clear()
//        notifyItemRangeChanged(getHeadersCount(), count)
        datas.addAll(data)
//        notifyItemInserted(getHeadersCount())
        notifyDataSetChanged()
    }

    fun add(data: List<T>) {
        val position = datas.size + getHeadersCount()
        datas.addAll(data)
        notifyItemInserted(position)
//        notifyDataSetChanged()
    }

    fun setClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.clickListener = onItemClickListener
    }

    fun getRealDataPosition(pos: Int): Int {
        return pos - getHeadersCount()
    }

    abstract fun convert(root: View, t: T?, position: Int, listener: OnItemClickListener<T>?)


    open fun getHeadersCount(): Int {
        return headers.size()
    }

    open fun getFootersCount(): Int {
        return footers.size()
    }

    open fun addHeaderView(view: View?) {
        headers.put(headers.size() + BASE_ITEM_TYPE_HEADER, view)
    }

    open fun addFootView(view: View?) {
        footers.put(footers.size() + BASE_ITEM_TYPE_FOOTER, view)
    }

    private fun isEmpty(): Boolean {
        return (mEmptyView != null || mEmptyLayoutId != 0) && datas.size == 0
    }

    private fun isHeaderViewPos(position: Int): Boolean {
        return position < getHeadersCount()
    }

    private fun isFooterViewPos(position: Int): Boolean {
        return position >= getHeadersCount() + datas.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY
        }

        if (isHeaderViewPos(position)) {
            return headers.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return footers.keyAt(position - getHeadersCount() - datas.size)
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (headers.get(viewType) != null) {
            return BaseViewHolder(headers.get(viewType))
        } else if (footers.get(viewType) != null) {
            return BaseViewHolder(footers.get(viewType))
        }

        if (isEmpty() && viewType == ITEM_TYPE_EMPTY) {
            return if (mEmptyView != null) {
                BaseViewHolder(mEmptyView)
            } else {
                BaseViewHolder(parent, mEmptyLayoutId)
            }
        }

        return BaseViewHolder(parent, id)
    }

    override fun getItemCount(): Int {//数据数 + header数 + footer数 + emptyView
        return datas.size + getHeadersCount() + getFootersCount() + if (isEmpty()) 1 else 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isEmpty() || isHeaderViewPos(position) || isFooterViewPos(position)) {
            return
        }
        holder.bindData(
            datas[getRealDataPosition(position)],
            getRealDataPosition(position),
            clickListener
        )
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager

        if (layoutManager is GridLayoutManager) {
            val spanSizeLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = getItemViewType(position)
                    return when {
                        headers.get(viewType) != null -> {
                            layoutManager.spanCount
                        }
                        footers.get(viewType) != null -> {
                            layoutManager.spanCount
                        }
                        ITEM_TYPE_EMPTY == viewType -> {
                            layoutManager.spanCount
                        }
                        else -> spanSizeLookup?.getSpanSize(position) ?: 1
                    }
                }
            }
            layoutManager.spanCount = layoutManager.spanCount
        }

    }


    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.layoutPosition
        if (isHeaderViewPos(position) || isFooterViewPos(position) || isEmpty()) {
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val p: StaggeredGridLayoutManager.LayoutParams = lp
                p.isFullSpan = true
            }
        }
    }


    inner class BaseViewHolder
        : RecyclerView.ViewHolder {

        constructor(view: View) : super(view)
        constructor(
            parent: ViewGroup,
            @LayoutRes resId: Int
        ) : super(LayoutInflater.from(parent.context).inflate(resId, parent, false))

        fun bindData(t: T?, position: Int, listener: OnItemClickListener<T>?) {
            convert(itemView, t, position, listener)
        }

    }

    interface OnItemClickListener<T> {
        fun click(t: T?, position: Int)
    }
}