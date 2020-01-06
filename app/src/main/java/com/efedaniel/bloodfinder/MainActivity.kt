package com.efedaniel.bloodfinder

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.LoadingCallback
import com.efedaniel.bloodfinder.extensions.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_indicator.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity(), LoadingCallback {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var currentFragment: BaseFragment
    private var dialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        toolbar.overflowIcon = getDrawable(R.drawable.ic_more_vert_24dp)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    fun setUpToolBar(toolbarTitle: String, showUpIcon: Boolean = true) {
        supportActionBar!!.run {
            setDisplayHomeAsUpEnabled(showUpIcon)
            setHomeAsUpIndicator(if (showUpIcon) R.drawable.ic_arrow_back_white_24dp else 0)
            toolbarTitleTextView.text = toolbarTitle
            val leftRightPaddingRes = if (!showUpIcon) R.dimen.toolbar_left_right_padding_root else R.dimen.toolbar_left_right_padding
            toolbarTitleTextView.setViewPadding(R.dimen.toolbar_top_bottom_padding, leftRightPaddingRes)
        }
    }

    fun hideToolbar() = includeToolbar.hide()

    fun showToolbar() = includeToolbar.show()

    fun setToolbarIcon(@DrawableRes resId: Int) {
        supportActionBar!!.setHomeAsUpIndicator(resId)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun showLoading() {
        showLoading(R.string.default_loading_message)
    }

    override fun showLoading(resId: Int) {
        showLoading(getString(resId))
    }

    private var loadingState = false

    override fun showLoading(message: String) {
        hideKeyBoard()
        progressMessage.text = message
        loading_layout_container.showViewWithChildren()
        toggleAnimation(true)
        disableTouch()
    }

    @SuppressLint("NewApi")
    private fun toggleAnimation(state: Boolean) {
        loadingState = state
        val drawable = heartRateAnimation.drawable
        if (drawable is AnimatedVectorDrawableCompat) {
            val callback = object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawab: Drawable?) { if (loadingState) drawable.start() }
            }
            drawable.registerAnimationCallback(callback)
            if (state) drawable.start() else drawable.stop()
        } else if (drawable is AnimatedVectorDrawable) {
            val callback = @TargetApi(Build.VERSION_CODES.M) object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawa: Drawable?) { if (loadingState) drawable.start() }
            }
            drawable.registerAnimationCallback(callback)
            if (state) drawable.start() else drawable.stop()
        }
    }

    override fun dismissLoading() {
        toggleAnimation(false)
        loading_layout_container.hide()
        enableTouch()
    }

    override fun showError(resId: Int) {
        showError(getString(resId))
    }

    override fun showError(message: String) {
        hideKeyBoard()
        dismissLoading()
        dialog?.dismiss()
        dialog = MaterialDialog(this).show {
            message(text = message)
            positiveButton(R.string.ok)
        }
    }

    fun invalidateToolbarElevation(scrollY: Int) {
        if (scrollY > (toolbar.measuredHeight / 2)) {
            appBarLayout.elevation = resources.getDimension(R.dimen.raised_toolbar_elevation)
        } else {
            appBarLayout.elevation = 0f
        }
    }

    fun showDialogWithAction(
        title: String?,
        body: String?,
        positiveRes: Int,
        positiveAction: (() -> Unit)?,
        negativeRes: Int?,
        negativeAction: (() -> Unit)?,
        cancelOnTouchOutside: Boolean
    ) {
        MaterialDialog(this).show {
            if (title != null) title(text = title)
            if (body != null) message(text = body)
            if (negativeRes != null) negativeButton(negativeRes) { negativeAction?.invoke() }
            positiveButton(positiveRes) { positiveAction?.invoke() }
            cancelOnTouchOutside(cancelOnTouchOutside)
        }
    }

    fun showSnackBar(message: String) {
        hideKeyBoard()
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun setCurrentFragment(baseFragment: BaseFragment) {
        currentFragment = baseFragment
    }

    override fun onBackPressed() {
        // If the current fragment doesn't consume the back pressed action, then call super onBackPressed
        if (!currentFragment.onBackPressed()) super.onBackPressed()
    }
}
