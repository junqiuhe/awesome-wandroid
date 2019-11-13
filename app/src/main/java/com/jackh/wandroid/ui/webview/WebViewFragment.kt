package com.jackh.wandroid.ui.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jackh.wandroid.R
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.databinding.FragmentWebViewBinding
import com.jackh.wandroid.ui.BaseFragment
import java.lang.RuntimeException
import java.net.URISyntaxException

/**
 *
 * WebView 相关知识
 * https://www.jianshu.com/p/3508789b3de5
 * https://www.jianshu.com/p/fcebd23cbebb
 * https://www.jianshu.com/p/fd61e8f4049e
 *
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/12 16:33
 * Description:
 */
class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    private lateinit var mUrl: String

    private var mTitle: String? = null

    private var mWebViewListener: OnWebViewListener? = null

    private val titleLiveData: MutableLiveData<String> = MutableLiveData()

    private val viewState: MutableLiveData<ViewState<String>> = MutableLiveData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWebViewListener) {
            mWebViewListener = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_web_view

    override fun initData(savedInstanceState: Bundle?) {
        mUrl = arguments?.getString(PARAMS_URL) ?: "https://www.wanandroid.com"

        mTitle = arguments?.getString(PARAMS_TITLE)
        mWebViewListener?.updateTitle(mTitle)


        viewState.observe(this, Observer { state: ViewState<String> ->
            when (state) {
                is ViewState.Success -> {
                    viewDataBinding.stateView.showContent()
                }
                is ViewState.Loading -> {
                    viewDataBinding.stateView.showLoading()
                }
                is ViewState.Failure -> {
                    viewDataBinding.stateView.showError()
                }
            }
        })

        titleLiveData.observe(this, Observer {
            if(mTitle == null){
                mWebViewListener?.updateTitle(it)
            }
        })

        viewDataBinding.webView.webViewClient = CustomWebViewClient(context!!, mUrl, viewState)

        viewDataBinding.webView.webChromeClient = CustomWebChromeClient(titleLiveData)

        val settings = viewDataBinding.webView.settings

        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = false
        settings.savePassword = false

//        if (AgentWebUtils.checkNetwork(webView.getContext())) {
//            //根据cache-control获取数据。
//            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT)
//        } else {
//            //没网，则从本地获取，即离线加载
//            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //适配5.0不允许http和https混合使用情况
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            viewDataBinding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            viewDataBinding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            viewDataBinding.webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        settings.textZoom = 100
        settings.databaseEnabled = true
        settings.setAppCacheEnabled(true)
        settings.loadsImagesAutomatically = true
        settings.setSupportMultipleWindows(false)
        // 是否阻塞加载网络图片  协议http or https
        settings.blockNetworkImage = false
        // 允许加载本地文件html  file协议
        settings.allowFileAccess = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            settings.allowFileAccessFromFileURLs = false
            // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            settings.allowUniversalAccessFromFileURLs = false
        }
        settings.javaScriptCanOpenWindowsAutomatically = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        } else {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        }
        settings.loadWithOverviewMode = false
        settings.useWideViewPort = true
        settings.domStorageEnabled = true
        settings.setNeedInitialFocus(true)
        settings.defaultTextEncodingName = "utf-8"//设置编码格式
        settings.defaultFontSize = 16
        settings.minimumFontSize = 12//设置 WebView 支持的最小字体大小，默认为 8
        settings.setGeolocationEnabled(true)

        //
//        val dir = AgentWebConfig.getCachePath(webView.getContext())
//        //设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
//        mWebSettings.setGeolocationDatabasePath(dir)
//        mWebSettings.setDatabasePath(dir)
//        mWebSettings.setAppCachePath(dir)

        //缓存文件最大值
        settings.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)

        viewDataBinding.webView.loadUrl(mUrl)

        viewDataBinding.stateView.setOnRetryBtnClickListener {
            viewDataBinding.webView.loadUrl(mUrl)
        }

        viewDataBinding.stateView.showContent()
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

private class CustomWebViewClient(
    private val context: Context,

    private val originUrl: String,

    private val viewState: MutableLiveData<ViewState<String>>

) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            request?.url?.scheme?.run {
                if (startsWith("https") || startsWith("http")) {
                    view?.loadUrl(request.url.toString())
                } else {
                    if (startsWith("intent")) {
                        handleInnerUrl(request.url.toString())
                    }
                }
                true
            } ?: super.shouldOverrideUrlLoading(view, request)

        } else {
            super.shouldOverrideUrlLoading(view, request)
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return url?.run {
            if (startsWith("https") || startsWith("http")) {
                view?.loadUrl(url)
            } else {
                if (startsWith("intent")) {
                    handleInnerUrl(url)
                }
            }
            true
        } ?: super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        viewState.postValue(ViewState.loading())
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        viewState.postValue(ViewState.success(""))
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            request?.isForMainFrame?.run {
                if (this) {
                    viewState.postValue(ViewState.failure(RuntimeException()))
                }
            }
        }
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        if (originUrl.toUpperCase() == failingUrl?.toUpperCase()) {
            viewState.postValue(ViewState.failure(RuntimeException()))
        }
    }

    private fun handleInnerUrl(url: String) {
        try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, R.string.can_not_open_app, Toast.LENGTH_SHORT).show()
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
}

private class CustomWebChromeClient(

    private val title: MutableLiveData<String>

) : WebChromeClient() {

    override fun onReceivedTitle(view: WebView?, t: String?) {
        t?.run {
            title.postValue(this)
        }
    }
}

interface OnWebViewListener {
    fun updateTitle(title: String?)
}