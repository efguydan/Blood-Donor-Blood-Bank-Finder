package com.efedaniel.bloodfinder.base

import androidx.annotation.StringRes

interface LoadingCallback {

    fun showLoading()

    fun showLoading(@StringRes resId: Int)

    fun showLoading(message: String)

    fun dismissLoading()

    fun showError(@StringRes resId: Int)

    fun showError(message: String)
}
