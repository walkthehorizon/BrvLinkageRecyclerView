# BrvLinkageRecyclerView

一个brv风格的左右联动RecyclerView，像写BrvAdapter一样轻松实现联动列表

## 内容列表

- [背景](#背景)
- [演示](#演示)
- [安装](#安装)
- [使用说明](#使用说明)
- [示例](#示例)
- [相关仓库](#相关仓库)
- [如何贡献](#如何贡献)
- [使用许可](#使用许可)

## 背景

实际需求中对于分级数据常常会使用左右联动的设计样式，为了简化该场景下的工作，实践中进行了组件化封装

这个仓库的目标是：

屏蔽内部联动逻辑，基于知名的[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)库设计Api,实现对原有Adapter写法的无缝对接

## 演示
![demo](/pictures/demo.gif)
## 安装
1. 添加JitPack到项目的根build文件
```grooy
allprojects {
  repositories {
	...
	maven { url 'https://jitpack.io' }
	}
}
```
1. 添加依赖到Module的build文件
```
dependencies {
  implementation 'com.github.walkthehorizon:BrvLinkageRecyclerView:+'
}
```
具体版本请查看release/tag


## 使用说明
1.编写xml文件
```xml
<com.shentu.brv_linkage_recyclerview.BrvLinkageRecyclerView
    android:id="@+id/linkRecyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
2.继承IPrimaryAdapter实现左部分Adapter
```kotlin
 class PrimaryAdapter(data: MutableList<Subject> = mutableListOf())
    :IPrimaryAdapter<Subject,BaseViewHolder>(R.layout.app_item_rv_subject,data){

    override fun convert(holder: BaseViewHolder, item: Subject) {
        if (isSelected(holder.layoutPosition)) {
           ...
        } else {
           ...
        }
    }
}
```
3.继承ISecondAdapter实现右部分Adapter
```kotlin
class SecondAdapter(data: MutableList<WallpaperSection> = mutableListOf()) :
    ISecondAdapter<WallpaperSection, BaseViewHolder>
        (R.layout.layout_common_head, R.id.tvHead, R.layout.app_item_rv_paper, data) {

    override fun convert(holder: BaseViewHolder, item: WallpaperSection) {
      ...
    }

    override fun convertHeader(helper: BaseViewHolder, item: WallpaperSection) {
        ...
    }
}
```
4.创建PrimaryAdapter和SecondAdapter实例并传递给BrvLinkageRecyclerView
```kotlin
binding.linkRecyclerView.setRvPrimaryAdapter(primaryAdapter)
binding.linkRecyclerView.setRvSecondAdapter(secondAdapter)
```

## 示例

想了解更多，请参考 [demo](https://github.com/walkthehorizon/BrvLinkageRecyclerView/blob/main/app/)。

## 相关仓库

- [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) — 强大而灵活的RecyclerView Adapter。


## 如何贡献

非常欢迎你的加入！[提一个 Issue](https://github.com/walkthehorizon/BrvLinkageRecyclerView/issues/new) 或者提交一个 Pull Request。


## 使用许可

[Apache License 2.0](LICENSE) © Walkthehorizon
