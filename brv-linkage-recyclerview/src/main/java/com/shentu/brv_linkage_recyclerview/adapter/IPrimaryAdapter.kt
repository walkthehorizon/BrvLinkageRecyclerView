package com.shentu.brv_linkage_recyclerview.adapter

import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem

abstract class IPrimaryAdapter<T : BasePrimaryItem>(
    val layoutResId: Int,
    val data: MutableList<T>
) :BaseLinkageAdapter<T>(){

    var selectedPosition = 0

    protected fun isSelected(position: Int): Boolean {
        return selectedPosition == position
    }
}