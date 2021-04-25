package com.shentu.brv_linkage_recyclerview.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem
import com.shentu.brv_linkage_recyclerview.bean.BaseSecondItem

abstract class ISecondAdapter <T : BaseSecondItem<*, BasePrimaryItem>, VH : BaseViewHolder>(
    val sectionHeadResId: Int,
    val headTextViewId: Int,
    val layoutResId: Int,
    val data: MutableList<T>
):BaseLinkageAdapter<T>(){

    abstract fun convertHeader(helper: VH, item: T)
}