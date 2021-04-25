package com.shentu.brvlinkagerecyclerview.brv

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brvlinkagerecyclerview.R
import com.shentu.brvlinkagerecyclerview.bean.WallpaperSection
import com.shentu.brv_linkage_recyclerview.adapter.ISecondAdapter

class SecondAdapter(data: MutableList<WallpaperSection> = mutableListOf()) :
    ISecondAdapter<WallpaperSection, BaseViewHolder>
        (R.layout.layout_common_head, R.id.tvHead, R.layout.app_item_rv_paper, data) {

    override fun convert(holder: BaseViewHolder, item: WallpaperSection) {
        holder.setText(R.id.tvName, item.data.name)
        holder.setText(R.id.tvDesc, item.data.desc)
        Glide.with(holder.itemView.context).load(item.data.url).into(holder.getView(R.id.ivPaper))
    }

    override fun convertHeader(helper: BaseViewHolder, item: WallpaperSection) {
        helper.setText(R.id.tvHead, item.header.title)
    }
}