package com.shentu.brvlinkagerecyclerview.brv

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brvlinkagerecyclerview.R
import com.shentu.brvlinkagerecyclerview.bean.Subject
import com.shentu.brv_linkage_recyclerview.adapter.IPrimaryAdapter

class PrimaryAdapter(data: MutableList<Subject> = mutableListOf())
    :IPrimaryAdapter<Subject,BaseViewHolder>(R.layout.app_item_rv_subject,data){

    override fun convert(holder: BaseViewHolder, item: Subject) {
        holder.setText(R.id.tvSubjectName, item.name)
        if (isSelected(holder.layoutPosition)) {
            holder.setBackgroundColor(
                R.id.tvSubjectName,
                ContextCompat.getColor(holder.itemView.context, R.color.pink)
            )
            holder.setTextColor(R.id.tvSubjectName, Color.WHITE)
        } else {
            holder.setBackgroundColor(
                R.id.tvSubjectName,
                Color.WHITE
            )
            holder.setTextColor(R.id.tvSubjectName, Color.BLACK)
        }
    }
}
