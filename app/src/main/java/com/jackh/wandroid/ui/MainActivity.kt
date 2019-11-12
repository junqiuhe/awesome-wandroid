package com.jackh.wandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.ActivityMainBinding
import com.jackh.wandroid.ui.main.MainFragment
import com.jackh.wandroid.ui.search.common.SearchFragment
import com.jackh.wandroid.ui.search.wxarticle_history.SearchWxArticleHistoryFragment
import com.jackh.wandroid.ui.webview.OnWebViewListener
import com.jackh.wandroid.utils.setupWithNavController
import com.jackh.wandroid.widget.CustomNavToolbar

class MainActivity : AppCompatActivity(), OnWebViewListener {

    private lateinit var customNavToolbar: CustomNavToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_ui)

        val parent: FrameLayout = findViewById(R.id.layout_container)

        DataBindingUtil.inflate<ActivityMainBinding>(
            LayoutInflater.from(this),
            R.layout.activity_main,
            parent,
            true
        )

        customNavToolbar = findViewById(R.id.custom_nav_tool_bar)
        if (actionBar == null) {
            val toolbar = customNavToolbar.getToolbar()
            toolbar.title = ""
            setSupportActionBar(toolbar)
        }

        setupWithNavController(customNavToolbar, findNavController(R.id.nav_host_fragment), genCustomAppBarConfig())
    }

    private fun genCustomAppBarConfig(): CustomAppBarConfiguration{
        return CustomAppBarConfiguration(
            listOf(
                MainFragment::class.java.canonicalName!!,
                SearchFragment::class.java.canonicalName!!,
                SearchWxArticleHistoryFragment::class.java.canonicalName!!
            )
        )
    }

    override fun updateTitle(title: String?) {
        title?.run {
            customNavToolbar.setTitle(this)
        }
    }
}