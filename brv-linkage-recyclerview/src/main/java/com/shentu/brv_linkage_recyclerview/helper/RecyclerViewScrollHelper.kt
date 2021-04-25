package com.shentu.brv_linkage_recyclerview.helper

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * 基于LinearSmoothScroller的指定位置滚动
 * */
object RecyclerViewScrollHelper {
    @JvmStatic
    fun smoothScrollToPosition(recyclerView: RecyclerView, snapMode: Int, position: Int) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            var mScroller: LinearSmoothScroller? = null
            mScroller = when (snapMode) {
                LinearSmoothScroller.SNAP_TO_START -> {
                    TopSmoothScroller(recyclerView.context)
                }
                LinearSmoothScroller.SNAP_TO_END -> {
                    BottomSmoothScroller(recyclerView.context)
                }
                else -> {
                    LinearSmoothScroller(recyclerView.context)
                }
            }
            mScroller.targetPosition = position
            layoutManager.startSmoothScroll(mScroller)
        }
    }

    class TopSmoothScroller internal constructor(context: Context?) :
        LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }

    class BottomSmoothScroller internal constructor(context: Context?) :
        LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_END
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_END
        }
    }
}