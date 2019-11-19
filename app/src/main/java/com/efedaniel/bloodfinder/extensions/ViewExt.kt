package com.efedaniel.bloodfinder.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun ViewGroup.showViewWithChildren() {
    show()
    for (view in children) { view.show() }
}

fun View.setViewPadding(@DimenRes topButtomPaddingRes: Int, @DimenRes leftRightPaddingRes: Int) {
    val leftRightPadding = context.resources.getDimension(leftRightPaddingRes).toInt()
    val topBottomPadding = context.resources.getDimension(topButtomPaddingRes).toInt()
    setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding)
}

fun RecyclerView.onScrollChanged(scrollListener: (Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            scrollListener(computeVerticalScrollOffset())
        }
    })
}

fun NestedScrollView.onScrollChanged(scrollListener: (Int) -> Unit) {
    setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
        scrollListener(scrollY)
    })
}

fun Spinner.registerTextViewLabel(labelTextView: TextView) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position > 0) labelTextView.show()
        }
    }
}
