package com.jackh.wandroid.ui.webview

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.*
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentWebViewBinding
import com.jackh.wandroid.ui.BaseFragment

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/12 16:33
 * Description:
 */

class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    private lateinit var mUrl: String

    private var mTitle: String? = null

    private var mWebViewListener: OnWebViewListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnWebViewListener){
            mWebViewListener = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_web_view

    override fun initData(savedInstanceState: Bundle?) {
        mUrl = arguments?.getString(PARAMS_URL) ?: "https://www.wanandroid.com"

        mTitle = arguments?.getString(PARAMS_TITLE)
        mWebViewListener?.updateTitle(mTitle)

        viewDataBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                viewDataBinding.stateView.showLoading()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                viewDataBinding.stateView.showContent()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                viewDataBinding.stateView.showError()
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                viewDataBinding.stateView.showError()
            }
        }
        viewDataBinding.webView.webChromeClient = object : WebChromeClient(){

            override fun onReceivedTitle(view: WebView?, title: String?) {
                if(mTitle == null){
                    mWebViewListener?.updateTitle(title)
                }
            }
        }

        val settings = viewDataBinding.webView.settings

        //支持App内部javascript交互
        settings.javaScriptEnabled = true
        //自适应屏幕
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.loadWithOverviewMode = true
        //设置可以支持缩放
        settings.setSupportZoom(true)
        //扩大比例的缩放
        settings.useWideViewPort = true
        //设置是否出现缩放工具
        settings.builtInZoomControls = true

        viewDataBinding.webView.loadUrl(mUrl)

        viewDataBinding.stateView.setOnRetryBtnClickListener {
            viewDataBinding.webView.loadUrl(mUrl)
        }
    }

    companion object {

        private const val PARAMS_URL = "url"

        private const val PARAMS_TITLE = "title"

        fun newInstance(url: String?, title: String? = null): WebViewFragment {
            val params = Bundle()
            params.putString(PARAMS_URL, url)
            params.putString(PARAMS_TITLE, title)

            val fragment = WebViewFragment()
            fragment.arguments = params
            return fragment
        }
    }
}

interface OnWebViewListener {
    fun updateTitle(title: String?)
}