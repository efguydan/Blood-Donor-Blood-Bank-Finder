package com.cottacush.android.androidbaseprojectkt.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cottacush.android.androidbaseprojectkt.networkutils.LoadingStatus


abstract class BaseViewModel() : ViewModel() {

    protected val _loadingStatus = MutableLiveData<LoadingStatus>()

    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus



    override fun onCleared() {
        super.onCleared()
    }
}
