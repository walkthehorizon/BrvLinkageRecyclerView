package com.shentu.brv_linkage_recyclerview.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem

abstract class IPrimaryAdapter<T : BasePrimaryItem, VH : BaseViewHolder>(
        val layoutResId: Int,
        val data: MutableList<T>
) : BaseLinkageAdapter<T, VH>() {

    var selectedPosition = 0

    protected fun isSelected(position: Int): Boolean {
        return selectedPosition == position
    }
}