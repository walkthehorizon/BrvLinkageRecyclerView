package com.shentu.brv_linkage_recyclerview

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.SectionEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shentu.brv_linkage_recyclerview.adapter.IPrimaryAdapter
import com.shentu.brv_linkage_recyclerview.adapter.ISecondAdapter
import com.shentu.brv_linkage_recyclerview.adapter.LinkagePrimaryAdapter
import com.shentu.brv_linkage_recyclerview.adapter.LinkageSecondAdapter
import com.shentu.brv_linkage_recyclerview.bean.BasePrimaryItem
import com.shentu.brv_linkage_recyclerview.bean.BaseSecondItem
import com.shentu.brv_linkage_recyclerview.helper.RecyclerViewScrollHelper.smoothScrollToPosition
import java.util.*

class BrvLinkageRecyclerView : ConstraintLayout {
    private var mContext: Context? = null
    private lateinit var rootView: ViewGroup
    private lateinit var rvPrimary: RecyclerView
    private lateinit var rvSecondary: RecyclerView
    lateinit var primaryAdapter: LinkagePrimaryAdapter
    private lateinit var secondaryAdapter: LinkageSecondAdapter
    private var tvHeader: TextView? = null
    private var headerContainer: FrameLayout? = null
    lateinit var headerLayout: View
    private val headerPositions: MutableList<Int> = ArrayList()
    private var titleHeight = 0
    private var firstVisiblePosition = 0
    private var lastGroupName: String? = null
    private lateinit var secondaryLayoutManager: LinearLayoutManager
    private lateinit var primaryLayoutManager: LinearLayoutManager

    /**
     * 是否启用滚动动画
     * @return
     */
    private var isScrollSmoothly = true

    private var primaryClicked = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }


    private fun initView(context: Context, attrs: AttributeSet?) {
        mContext = context
        rootView =
            LayoutInflater.from(context).inflate(R.layout.layout_linkage_view, this) as ViewGroup
        rvPrimary = rootView.findViewById(R.id.rvPrimary)
        rvSecondary = rootView.findViewById<View>(R.id.rvSecondary) as RecyclerView
        headerContainer = rootView.findViewById<View>(R.id.headerContainer) as FrameLayout
        primaryLayoutManager = LinearLayoutManager(mContext)
        rvPrimary.layoutManager = primaryLayoutManager
        secondaryLayoutManager = LinearLayoutManager(mContext)
        rvSecondary.layoutManager = secondaryLayoutManager
    }

    fun setRvPrimaryAdapter(adapter: IPrimaryAdapter<*>) {
        primaryAdapter = LinkagePrimaryAdapter(adapter as IPrimaryAdapter<BasePrimaryItem>)
        primaryAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            primaryAdapter.onItemClick(adapter, view, position)
            if (isScrollSmoothly) {
                smoothScrollToPosition(
                    rvSecondary,
                    LinearSmoothScroller.SNAP_TO_START,
                    headerPositions[position]
                )
            } else {
                secondaryLayoutManager.scrollToPositionWithOffset(
                    headerPositions[position], 0
                )
            }
            primaryAdapter.setSelectedPosition(position)
            primaryClicked = true
        }
        rvPrimary.adapter = primaryAdapter
    }

    fun setRvSecondAdapter(adapter: ISecondAdapter<*, *>) {
        secondaryAdapter =
            LinkageSecondAdapter(adapter as ISecondAdapter<BaseSecondItem<*, BasePrimaryItem>, BaseViewHolder>)
        rvSecondary.adapter = secondaryAdapter

        // confirm section position in SecondAdapter
        for (i in adapter.data.indices) {
            val item = adapter.data[i] as BaseSecondItem<*, *>
            if (item.isHeader) {
                headerPositions.add(i)
            }
        }
        initLinkageSecondary()
        setRvSecondBottomFill()
    }


    /**
     * 设置RvSecond的Manager,默认使用LinearManager
     * */
    fun setSecondLayoutManager(manager: RecyclerView.LayoutManager) {
        require(manager is LinearLayoutManager) {
            "manager must be LinearLayoutManager or GridLayoutManager!"
        }
        secondaryLayoutManager = manager
        rvSecondary.layoutManager = secondaryLayoutManager
    }

    private fun initLinkageSecondary() {
        if (secondaryAdapter.data.isEmpty()) {
            //ignore
            return
        }
        // Note: headerLayout is shared by both SecondaryAdapter's header and HeaderView
        if (tvHeader == null) {
            headerLayout = LayoutInflater.from(mContext)
                .inflate(secondaryAdapter.getSectionHeadResId(), headerContainer, false)
            headerContainer!!.addView(headerLayout)
            tvHeader = headerLayout.findViewById(secondaryAdapter.getHeadTextViewId())
        }
        if (secondaryAdapter.data[firstVisiblePosition].isHeader) {
            tvHeader!!.text = secondaryAdapter.data[firstVisiblePosition].header.title
        }
        rvSecondary.clearOnScrollListeners()
        rvSecondary.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                titleHeight = headerContainer!!.measuredHeight
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstPosition = secondaryLayoutManager.findFirstVisibleItemPosition()
                val firstCompletePosition =
                    secondaryLayoutManager.findFirstCompletelyVisibleItemPosition()
                val items: List<BaseSecondItem<*, BasePrimaryItem>> = secondaryAdapter.data

                // 粘性标签平移
                if (firstCompletePosition > 0 && firstCompletePosition < items.size && items[firstCompletePosition].isHeader) {
                    val view = secondaryLayoutManager.findViewByPosition(firstCompletePosition)
                    if (view != null && view.top <= titleHeight) {
                        headerContainer!!.y = (view.top - titleHeight).toFloat()
                    }
                } else {
                    headerContainer!!.y = 0f
                }

                // 粘性标签内容替换
                var groupNameChanged = false
                if (firstVisiblePosition != firstPosition && firstPosition >= 0) {
                    if (firstVisiblePosition < firstPosition) {
                        headerContainer!!.y = 0f
                    }
                    firstVisiblePosition = firstPosition
                    val currentGroupName = items[firstVisiblePosition].header.title
                    if (TextUtils.isEmpty(lastGroupName) || lastGroupName != currentGroupName) {
                        lastGroupName = currentGroupName
                        groupNameChanged = true
                        tvHeader!!.text = lastGroupName
                    }
                }

                // 当主动点击primary触发second滚动时，屏蔽second滚动对primary选中状态的影响，
                // 非主动点击则平滑滚动primary到目标position
                if (groupNameChanged) {
                    val groupNames = primaryAdapter.getStrings()
                    for (i in groupNames.indices) {
                        if (groupNames[i] == lastGroupName) {
                            if (primaryClicked) {
                                if (primaryAdapter.getSelectedPosition() == i) {
                                    primaryClicked = false
                                }
                            } else {
                                primaryAdapter.setSelectedPosition(i)
                                smoothScrollToPosition(
                                    rvPrimary,
                                    LinearSmoothScroller.SNAP_TO_END, i
                                )
                            }
                        }
                    }
                }
            }
        })
    }

    /**
     * 设置左右RecyclerView的宽度比
     *
     * @param percent
     */
    fun setPercent(percent: Float) {
        val guideline: Guideline = rootView.findViewById(R.id.guideline)
        guideline.setGuidelinePercent(percent)
    }

    fun setRvPrimaryBackground(color: Int) {
        rvPrimary.setBackgroundColor(color)
    }

    fun setRvSecondaryBackground(color: Int) {
        rvSecondary.setBackgroundColor(color)
    }

    fun addRvPrimaryItemDecoration(decoration: RecyclerView.ItemDecoration?) {
        rvPrimary.removeItemDecoration(decoration!!)
        rvPrimary.addItemDecoration(decoration)
    }

    fun addRvSecondItemDecoration(decoration: RecyclerView.ItemDecoration?) {
        rvSecondary.removeItemDecoration(decoration!!)
        rvSecondary.addItemDecoration(decoration)
    }

    /**
     * 设置底部空白部分UI效果
     * */
    fun setRvSecondBottomFill(view: View? = null) {
        if (secondaryAdapter.data.size < 2) {
            //ignore
            return
        }
        // 计算最后一个Section及其子项的总高度,若小于secondaryRv的高度，则进行空白填充
        rvSecondary.post {
            val spanCount =
                if (secondaryLayoutManager is GridLayoutManager) (secondaryLayoutManager as GridLayoutManager).spanCount else 1
            val sectionHeight =
                secondaryLayoutManager.getDecoratedMeasuredHeight(rvSecondary.getChildAt(0))
            var itemHeight = 0
            for (i in 0..rvSecondary.childCount) {
                val viewType = secondaryLayoutManager.getItemViewType(rvSecondary.getChildAt(i))
                if (viewType == SectionEntity.NORMAL_TYPE) {
                    itemHeight = rvSecondary.getChildAt(i).height
                    break
                }
            }
            val endSectionTotalHeight =
                (secondaryAdapter.data.size - 1 - headerPositions[headerPositions.size - 1]) * itemHeight / spanCount + sectionHeight
            if (endSectionTotalHeight < rvSecondary.measuredHeight) {
                val addHeight = rvSecondary.height - endSectionTotalHeight
                val footer = view ?: FrameLayout(context)
                val lp = ViewGroup.LayoutParams(-1, addHeight)
                footer.layoutParams = lp
                secondaryAdapter.addFooterView(footer)
            }
        }
    }
}