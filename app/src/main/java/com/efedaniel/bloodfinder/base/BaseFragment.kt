package com.efedaniel.bloodfinder.base

import androidx.fragment.app.Fragment
import com.efedaniel.bloodfinder.MainActivity

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }
}
