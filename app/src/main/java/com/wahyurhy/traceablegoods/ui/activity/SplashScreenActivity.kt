package com.wahyurhy.traceablegoods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.MainActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.utils.Utils

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        fitStatusBar()
        startSplashScreen()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    private fun startSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }, 1000)
    }
}