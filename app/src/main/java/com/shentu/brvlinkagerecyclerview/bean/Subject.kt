package com.shentu.brvlinkagerecyclerview.bean

import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem

data class Subject(
    val name: String = "",
    val url:String = "",
    val desc:String = "我说道：“爸爸，你走吧。”他往车外看了看，说：“我买几个橘子去。你就在此地，不要走动。”",
    val papers: MutableList<Wallpaper> = mutableListOf()
) : BasePrimaryItem {
    override val title: String
        get() = name
}
