package com.efedaniel.bloodfinder.utils

import android.app.Application
import javax.inject.Inject

/*Disclaimer - This class intended to be used by view models to provide common resources (Strings, ints, colors etc)
The class will require the app to be restarted for a locale or configuration change to reflect.
The class can not provide Activity scoped theme/styles related resources*/

class ResourceProvider @Inject constructor(private val context: Application) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return context.getString(resId, value)
    }
}
