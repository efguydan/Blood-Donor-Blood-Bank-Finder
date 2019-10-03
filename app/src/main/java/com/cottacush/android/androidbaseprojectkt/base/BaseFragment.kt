package com.cottacush.android.androidbaseprojectkt.base

import androidx.fragment.app.Fragment
import com.cottacush.android.androidbaseprojectkt.MainActivity

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }
}
