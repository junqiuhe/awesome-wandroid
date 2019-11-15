package com.jackh.wandroid.repository

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/15 9:21
 * Description:
 */

class LikesManager private constructor() {

    companion object {

        private var mInstance: LikesManager? = null

        fun getInstance(): LikesManager {
            return synchronized(LikesManager::class) {
                if (mInstance == null) {
                    mInstance = LikesManager()
                }
                mInstance!!
            }
        }
    }
}