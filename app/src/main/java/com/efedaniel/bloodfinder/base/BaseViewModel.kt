package com.efedaniel.bloodfinder.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efedaniel.bloodfinder.networkutils.LoadingStatus


abstract class BaseViewModel() : ViewModel() {

    protected val _loadingStatus = MutableLiveData<LoadingStatus>()

    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus



    override fun onCleared() {
        super.onCleared()
    }
}
