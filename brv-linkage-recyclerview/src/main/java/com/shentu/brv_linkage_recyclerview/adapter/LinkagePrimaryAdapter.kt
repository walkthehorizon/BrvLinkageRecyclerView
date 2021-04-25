package com.shentu.brv_linkage_recyclerview.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem

class LinkagePrimaryAdapter(
    private val outAdapter: IPrimaryAdapter<BasePrimaryItem>
) : BaseQuickAdapter<BasePrimaryItem, BaseViewHolder>(
    outAdapter.layoutResId, outAdapter.data
) {
    private val titles: MutableList<String> = mutableListOf()

    init {
        data.forEach {
            titles.add(it.title)
        }
        setOnItemChildClickListener(outAdapter.getOnItemChildClickListener())
        setOnItemLongClickListener(outAdapter.getOnItemLongClickListener())
        setOnItemChildLongClickListener(outAdapter.getOnItemChildLongClickListener())
        outAdapter.childClickViewIds.forEach {
            addChildClickViewIds(it)
        }
        outAdapter.childLongClickViewIds.forEach {
            addChildLongClickViewIds(it)
        }
    }

    fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        outAdapter.getOnItemClickListener()?.onItemClick(adapter, view, position)
    }

    fun setSelectedPosition(position: Int) {
        outAdapter.selectedPosition = position
        notifyDataSetChanged()
    }

    fun getStrings(): List<String> {
        return titles
    }

    override fun convert(holder: BaseViewHolder, item: BasePrimaryItem) {
        outAdapter.convert(holder, item)
    }

    fun getSelectedPosition(): Int {
        return outAdapter.selectedPosition
    }
}