package com.efedaniel.bloodfinder.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.view.children

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun ViewGroup.showViewWithChildren() {
    show()
    for (view in children) {
        view.show()
    }
}

fun View.setViewPadding(@DimenRes topButtomPaddingRes: Int, @DimenRes leftRightPaddingRes: Int) {
    val leftRightPadding = context.resources.getDimension(leftRightPaddingRes).toInt()
    val topBottomPadding = context.resources.getDimension(topButtomPaddingRes).toInt()
    setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding)
}
