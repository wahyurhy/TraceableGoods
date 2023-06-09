package com.wahyurhy.traceablegoods.ui.activity

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityForgotBinding
import com.wahyurhy.traceablegoods.utils.Utils

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimation()
        fitStatusBar()
        initClickListener()
    }

    private fun initAnimation() {
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        animIn.duration = 300
        binding.ivForgot.startAnimation(animIn)
    }

    private fun initClickListener() {
        binding.btnSubmit.setOnClickListener {
            Toast.makeText(this, "Submit", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }
}