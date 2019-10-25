package com.jackh.wandroid.network

import com.jackh.wandroid.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 9:42
 * Description:
 */
class RetrofitClient private constructor() {

    private val retrofit: Retrofit

    init {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder().baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    companion object {

        private var mInstance: RetrofitClient? = null

        fun getInstance(): RetrofitClient {
            return synchronized(RetrofitClient::class.java) {
                if (mInstance == null) {
                    mInstance = RetrofitClient()
                }
                mInstance!!
            }
        }
    }
}