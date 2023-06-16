package com.wahyurhy.traceablegoods.ui.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.MainObserverActivity
import com.wahyurhy.traceablegoods.databinding.ActivityLoginBinding
import com.wahyurhy.traceablegoods.utils.Utils

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimation()
        fitStatusBar()
        initClickListener()
    }

    private fun initAnimation() {
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        animIn.duration = 300
        binding.ivLogoApp.startAnimation(animIn)
    }

    private fun initClickListener() {
        binding.edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.scrollView.smoothScrollTo(0, 1000)
            }
        }
        binding.btnObserver.setOnClickListener {
            Toast.makeText(this, "Observer", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainObserverActivity::class.java))
        }
    }

    private fun animOut() {
        val animOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        binding.ivLogoApp.startAnimation(animOut)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }
}