package com.efedaniel.bloodfinder.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.efedaniel.bloodfinder.MainActivity
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

    override fun onStart() {
        super.onStart()
        mainActivity.setCurrentFragment(this)
        if (getViewModel().loadingStatus.hasObservers()) return
        getViewModel().loadingStatus.observe(this, Observer {
            when (it) {
                LoadingStatus.Success -> mainActivity.dismissLoading()
                is LoadingStatus.Loading -> mainActivity.showLoading(it.message)
                is LoadingStatus.Error -> {
                    mainActivity.showError(it.errorMessage)
                    getViewModel().errorShown()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getViewModel().addAllLiveDataToObservablesList()
        for (liveData in getViewModel().observablesList) liveData.removeObservers(this)
    }

    //Return true if you handle the back press in your fragment
    open fun onBackPressed(): Boolean {
        return false
    }

    abstract fun getViewModel(): BaseViewModel
}
