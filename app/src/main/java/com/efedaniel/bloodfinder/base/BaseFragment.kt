package com.efedaniel.bloodfinder.base

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.efedaniel.bloodfinder.MainActivity
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.networkutils.LoadingStatus

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
        getViewModel().snackMessage.observe(this, Observer {
            if (it != null) {
                showSnackbar(it)
                getViewModel().snackMessageShown()
            }
        })
    }

    fun showDialogWithAction(
        title: String? = null,
        body: String? = null,
        @StringRes positiveRes: Int = R.string.ok,
        positiveAction: (() -> Unit)? = null,
        @StringRes negativeRes: Int? = null,
        negativeAction: (() -> Unit)? = null,
        cancelOnTouchOutside: Boolean = false
    ) = mainActivity.showDialogWithAction(title, body, positiveRes, positiveAction, negativeRes, negativeAction, cancelOnTouchOutside)

    fun showSnackbar(@StringRes stringRes: Int) = try {
        mainActivity.showSnackBar(getString(stringRes))
    } catch (e: Resources.NotFoundException) {
        showSnackbar(stringRes.toString())
    }

    fun showSnackbar(message: String) {
        mainActivity.showSnackBar(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getViewModel().addAllLiveDataToObservablesList()
        for (liveData in getViewModel().observablesList) liveData.removeObservers(this)
    }

    // Return true if you handle the back press in your fragment
    open fun onBackPressed(): Boolean {
        return false
    }

    abstract fun getViewModel(): BaseViewModel
}
