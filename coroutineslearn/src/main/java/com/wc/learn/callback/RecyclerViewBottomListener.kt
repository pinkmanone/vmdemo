package com.wc.learn.callback

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @auther AndroidDeveloper
 * @data 2018/1/29/0029
 */
abstract class RecyclerViewBottomListener : RecyclerView.OnScrollListener() {
    enum class LAYOUT_MANAGER_TYPE {
        LINEAR, GRID, STAGGERED_GRID
    }

    /**
     * layoutManager的类型（枚举）
     */
    protected var layoutManagerType: LAYOUT_MANAGER_TYPE? = null

    /**
     * 最后一个的位置
     */
    private var lastPositions: IntArray?=null

    /**
     * 最后一个可见的item的位置
     */
    private var lastVisibleItemPosition = 0

    /**
     * 当前滑动的状态
     */
    private var currentScrollState = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        if (layoutManagerType == null) {
            layoutManagerType = if (layoutManager is GridLayoutManager) {
                LAYOUT_MANAGER_TYPE.GRID
            } else if (layoutManager is LinearLayoutManager) {
                LAYOUT_MANAGER_TYPE.LINEAR
            } else if (layoutManager is StaggeredGridLayoutManager) {
                LAYOUT_MANAGER_TYPE.STAGGERED_GRID
            } else {
                throw RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }
        when (layoutManagerType) {
            LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
            LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager?)!!.findLastVisibleItemPosition()
            LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager?
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager!!.spanCount)
                }
                staggeredGridLayoutManager!!.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        currentScrollState = newState
        val layoutManager = recyclerView.layoutManager
        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager.itemCount
        if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1 && visibleItemCount <= totalItemCount) {
            onBottom()
        }
    }

    abstract fun onBottom()
    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }
}