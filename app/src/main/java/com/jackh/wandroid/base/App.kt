package com.jackh.wandroid.base

import android.app.Application
import android.content.Context
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.repository.SearchRepository

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 11:38
 * Description:
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        mApplication = this

        AccountManager.getInstance().init()

        SearchRepository.getInstance().preloadHotSearchInfo()
    }

    companion object {

        private lateinit var mApplication: App

        fun getContext(): Context {
            return mApplication
        }
    }
}