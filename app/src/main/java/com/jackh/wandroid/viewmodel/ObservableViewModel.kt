package com.jackh.wandroid.viewmodel

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 15:42
 * Description:
 */
open class ObservableViewModel : BaseViewModel(), Observable{

    private val callbacks: PropertyChangeRegistry by lazy {
        PropertyChangeRegistry()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}