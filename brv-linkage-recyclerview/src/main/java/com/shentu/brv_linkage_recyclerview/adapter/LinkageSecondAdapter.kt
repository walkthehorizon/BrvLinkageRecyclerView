package com.shentu.brv_linkage_recyclerview.adapter

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem
import com.shentu.brv_linkage_recyclerview.bean.BaseSecondItem

class LinkageSecondAdapter(
    private val outAdapter: ISecondAdapter<BaseSecondItem<*, BasePrimaryItem>, BaseViewHolder>,
) : BaseSectionQuickAdapter<BaseSecondItem<*, BasePrimaryItem>, BaseViewHolder>(
    outAdapter.sectionHeadResId,
    outAdapter.layoutResId,
    outAdapter.data
) {

    init {
        outAdapter.childClickViewIds.forEach {
            addChildClickViewIds(it)
        }
        outAdapter.childLongClickViewIds.forEach {
            addChildLongClickViewIds(it)
        }
        setOnItemClickListener(outAdapter.getOnItemClickListener())
        setOnItemChildClickListener(outAdapter.getOnItemChildClickListener())
        setOnItemLongClickListener(outAdapter.getOnItemLongClickListener())
        setOnItemChildLongClickListener(outAdapter.getOnItemChildLongClickListener())
    }

    override fun convert(holder: BaseViewHolder, item: BaseSecondItem<*, BasePrimaryItem>) {
        outAdapter.convert(holder, item)
    }

    override fun convertHeader(helper: BaseViewHolder, item: BaseSecondItem<*, BasePrimaryItem>) {
        outAdapter.convertHeader(helper, item)
    }

    fun getSectionHeadResId(): Int {
        return outAdapter.sectionHeadResId
    }

    fun getHeadTextViewId(): Int {
        return outAdapter.headTextViewId
    }
}