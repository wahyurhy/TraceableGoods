package com.wahyurhy.traceablegoods.utils

import android.app.Activity
import android.view.WindowManager
import com.wahyurhy.traceablegoods.R

object Utils {
    fun setSystemBarFitWindow(activity: Activity) {
        activity.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
}