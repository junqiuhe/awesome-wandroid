package com.jackh.wandroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jackh.wandroid.repository.*
import com.jackh.wandroid.viewmodel.account.HistoryViewModel
import com.jackh.wandroid.viewmodel.account.LoginViewModel
import com.jackh.wandroid.viewmodel.account.MineViewModel
import com.jackh.wandroid.viewmodel.account.RegisterViewModel
import com.jackh.wandroid.viewmodel.main.*
import com.jackh.wandroid.viewmodel.search.SearchViewModel
import com.jackh.wandroid.viewmodel.search.SearchWxArticleHistoryViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 15:00
 * Description:
 */
class CustomViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(clazz: Class<T>): T {
        return when {

            LoginViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(UserRepository::class.java)
                    .newInstance(UserRepository.getInstance())
            }

            RegisterViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(UserRepository::class.java)
                    .newInstance(UserRepository.getInstance())
            }

            LatestArticleViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(HomeRepository::class.java)
                    .newInstance(HomeRepository.getInstance())
            }

            ProjectViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(ProjectRepository::class.java)
                    .newInstance(ProjectRepository.getInstance())
            }

            ProjectListViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(ProjectRepository::class.java)
                    .newInstance(ProjectRepository.getInstance())
            }

            LatestProjectViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(ProjectRepository::class.java)
                    .newInstance(ProjectRepository.getInstance())
            }

            WxArticleListViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(WxPublishNumRepository::class.java)
                    .newInstance(WxPublishNumRepository.getInstance())
            }

            WxPublishNumViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(WxPublishNumRepository::class.java)
                    .newInstance(WxPublishNumRepository.getInstance())
            }

            SearchViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(SearchRepository::class.java)
                    .newInstance(SearchRepository.getInstance())
            }

            SearchWxArticleHistoryViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(
                    SearchRepository::class.java,
                    WxPublishNumRepository::class.java
                )
                    .newInstance(
                        SearchRepository.getInstance(),
                        WxPublishNumRepository.getInstance()
                    )
            }

            MineViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(UserRepository::class.java)
                    .newInstance(UserRepository.getInstance())
            }

            SettingViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.newInstance()
            }

            QRViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.newInstance()
            }

            HistoryViewModel::class.java.isAssignableFrom(clazz) ->{
                clazz.newInstance()
            }

            else -> throw IllegalArgumentException("CustomViewModelFactory create method illegal argument")
        }
    }

    companion object {

        private var factory: CustomViewModelFactory? = null

        fun getCustomViewModelFactory(context: Context): CustomViewModelFactory {
            return synchronized(CustomViewModelFactory::class.java) {
                if (factory == null) {
                    factory = CustomViewModelFactory(context.applicationContext)
                }
                factory!!
            }
        }
    }
}