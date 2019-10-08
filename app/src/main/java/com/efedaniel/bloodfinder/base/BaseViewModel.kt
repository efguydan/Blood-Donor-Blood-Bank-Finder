package com.efedaniel.bloodfinder.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efedaniel.bloodfinder.networkutils.LoadingStatus

abstract class BaseViewModel : ViewModel() {

    val observablesList: MutableList<LiveData<*>> = mutableListOf()

    protected val _loadingStatus = MutableLiveData<LoadingStatus>()

    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    fun errorShown() {
        _loadingStatus.value = null
    }

    protected val _snackMessage = MutableLiveData<String>()

    val snackMessage: LiveData<String>
        get() = _snackMessage

    fun snackMessageShown() {
        _snackMessage.value = null
    }

    /**
     * We want to add all observables to the list so that they are removed when the view is destroyed
     * If this isn't done, we would have multiple observers on the same variable which might lead to a crash
     */
    abstract fun addAllLiveDataToObservablesList()
}
