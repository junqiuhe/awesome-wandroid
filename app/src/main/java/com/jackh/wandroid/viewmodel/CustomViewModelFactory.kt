package com.jackh.wandroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jackh.wandroid.repository.HomeRepository
import com.jackh.wandroid.repository.ProjectListRepository
import com.jackh.wandroid.repository.ProjectRepository
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.account.LoginViewModel
import com.jackh.wandroid.viewmodel.account.RegisterViewModel
import com.jackh.wandroid.viewmodel.main.LatestArticleViewModel
import com.jackh.wandroid.viewmodel.main.LatestProjectViewModel
import com.jackh.wandroid.viewmodel.main.ProjectListViewModel
import com.jackh.wandroid.viewmodel.main.ProjectViewModel

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
                    .newInstance(UserRepository.getInstance(context))
            }

            RegisterViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(UserRepository::class.java)
                    .newInstance(UserRepository.getInstance(context))
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
                clazz.getConstructor(ProjectListRepository::class.java)
                    .newInstance(ProjectListRepository.getInstance())
            }

            LatestProjectViewModel::class.java.isAssignableFrom(clazz) -> {
                clazz.getConstructor(ProjectListRepository::class.java)
                    .newInstance(ProjectListRepository.getInstance())
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