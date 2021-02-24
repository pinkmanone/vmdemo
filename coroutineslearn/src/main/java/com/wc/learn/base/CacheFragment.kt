package com.wc.learn.base

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

abstract class CacheFragment : Fragment() {

    protected var mRootView: View? = null
    protected var mViewCreated = false

    private var mCacheViews: SparseArray<View>? = null

    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (useDataBinding()) {
            val view = initBinding(inflater, container)
            mRootView = view
            mViewCreated = true
            return view
        } else {
            if (mRootView == null) {
                val layoutId: Int = getLayoutRes()
                if (layoutId > 0) {
                    mRootView = inflater.inflate(getLayoutRes(), container, false)
                }
            }
            mViewCreated = true
            mRootView
        }

    }

    open fun useDataBinding(): Boolean {
        return false
    }

    open fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
        if (mCacheViews != null) {
            mCacheViews?.clear()
            mCacheViews = null
        }
        mViewCreated = false
    }

    @Nullable
    open fun getRootView(): View? {
        return mRootView
    }

    fun <V : View?> getView(@IdRes id: Int): V? {
        if (mCacheViews == null) {
            mCacheViews = SparseArray()
        }
        var view = mCacheViews?.get(id)
        if (view == null) {
            view = findViewById(id)
            if (view != null) {
                mCacheViews?.put(id, view)
            }
        }
        return view as V
    }

    fun <V : View?> findViewById(@IdRes id: Int): V? {
        return if (mRootView == null) null
        else mRootView?.findViewById<V>(id)
    }

}