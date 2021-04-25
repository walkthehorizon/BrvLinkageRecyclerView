package com.shentu.brvlinkagerecyclerview.bean

import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem
import com.shentu.brv_linkage_recyclerview.bean.BaseSecondItem

class WallpaperSection(
    override val header: Subject = Subject(),
    override val data: Wallpaper = Wallpaper(),
    override val isHeader: Boolean
) : BaseSecondItem<Wallpaper, BasePrimaryItem>