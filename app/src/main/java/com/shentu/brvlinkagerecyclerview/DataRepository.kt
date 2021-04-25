package com.shentu.brvlinkagerecyclerview

import com.shentu.brvlinkagerecyclerview.bean.Subject
import com.shentu.brvlinkagerecyclerview.bean.Wallpaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object DataRepository {

    private val subjects = mutableListOf(
        Subject(
            "火影忍者",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/5207/493dc4ff91503674235ec4b3362a57ec5a6f2b14.jpg"
        ),
        Subject(
            "工作细胞",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/63/0b4b2562731824d4acab18e023be31c963219adb.jpg"
        ),
        Subject(
            "英雄联盟",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/265/925288fdf1fbd5200f8e0393582bb08fc63093a7.jpg"
        ),
        Subject(
            "王者荣耀",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/3009/15fc62b62f5d07c1ae3a5574a215a7ab216be22d.jpg"
        ),
        Subject(
            "一人之下",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/5255/ea2ca896d819de784871e1fb6b0785fcd6ce9428.jpg"
        ),
        Subject(
            "最终幻想",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/2665/0345fe9f4cec404e6cd5dcd332e994e0c4e713ac.jpg"
        ),
        Subject(
            "刀剑神域",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/7576/536ca836700e3cdcafa82e4c51e8edab9c3e3d40.jpg"
        ),
        Subject(
            "剑侠情缘",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/24/9b31b4dec651fc2102c8b071b1b08550918ef27f.jpg"
        ),
        Subject(
            "古剑奇谭",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/8420/dccfa40fb98612c16240285a4ab2fdf68bd4c03e.jpg"
        ),
        Subject(
            "碧蓝航线",
            "https://wallpager-1251812446.cos.ap-beijing.myqcloud.com/image/121/e3dcf4d161b666030833a66104c308a9bee644ed.jpg"
        )
    )

    init {
        subjects.forEach {
            for (i in 0..3) {
                it.papers.add(
                    Wallpaper(
                        it.name,
                        it.url,
                        it.desc
                    )
                )
            }
        }
    }

    suspend fun getSubjects(): MutableList<Subject> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            subjects
        }
    }
}