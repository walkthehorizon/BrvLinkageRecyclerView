package com.shentu.brvlinkagerecyclerview.brv

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.shentu.brvlinkagerecyclerview.DataRepository
import com.shentu.brvlinkagerecyclerview.R
import com.shentu.brvlinkagerecyclerview.bean.Subject
import com.shentu.brvlinkagerecyclerview.bean.WallpaperSection
import com.shentu.brvlinkagerecyclerview.databinding.ActivityDemoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DemoActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var binding: ActivityDemoBinding
    private lateinit var primaryAdapter: PrimaryAdapter
    private lateinit var secondAdapter: SecondAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.linkRecyclerView.setRvPrimaryBackground(
            ContextCompat.getColor(
                this,
                R.color.color_background
            )
        )
        loadData()
    }

    private fun loadData() {
        launch {
            val subjects = DataRepository.getSubjects()
            binding.progressBar.visibility = View.GONE
            val paperSections = mutableListOf<WallpaperSection>()
            subjects.forEach {
                paperSections.add(WallpaperSection(it, isHeader = true))
                it.papers.forEach { paper ->
                    paperSections.add(WallpaperSection(it, paper, false))
                }
            }
            showLinkageView(subjects, paperSections)
        }
    }

    private fun showLinkageView(
        subjects: MutableList<Subject>,
        paperSections: MutableList<WallpaperSection>
    ) {
        primaryAdapter = PrimaryAdapter(subjects)
//        primaryAdapter.setOnItemClickListener { adapter, _, position ->
//            Snackbar.make(
//                binding.root,
//                "primary_" + (adapter.data[position] as Subject).name,
//                LENGTH_SHORT
//            ).show()
//        }
        secondAdapter = SecondAdapter(paperSections)
        secondAdapter.setOnItemClickListener { adapter, _, position ->
            Snackbar.make(
                binding.root,
                "second_" + (adapter.data[position] as WallpaperSection).data.name,
                LENGTH_SHORT
            ).show()
        }
        secondAdapter.addChildClickViewIds(R.id.ivPaper)
        secondAdapter.setOnItemChildClickListener { _, _, _ ->
            Snackbar.make(binding.root, "image_click", LENGTH_SHORT).show()
        }
        binding.linkRecyclerView.setRvPrimaryAdapter(primaryAdapter)
        binding.linkRecyclerView.setRvSecondAdapter(secondAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}