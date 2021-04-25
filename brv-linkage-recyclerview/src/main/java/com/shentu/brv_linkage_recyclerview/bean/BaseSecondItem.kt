package com.shentu.brv_linkage_recyclerview.bean

import com.chad.library.adapter.base.entity.SectionEntity

interface BaseSecondItem<T, S : BasePrimaryItem> : SectionEntity {
    val data: T
    val header: S
}