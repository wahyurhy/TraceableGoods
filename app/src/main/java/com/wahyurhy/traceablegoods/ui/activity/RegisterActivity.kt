package com.wahyurhy.traceablegoods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityRegisterBinding
import com.wahyurhy.traceablegoods.utils.Utils

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimation()
        fitStatusBar()
        initClickListener()
    }

    private fun initAnimation() {
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        animIn.duration = 300
        binding.ivRegist.startAnimation(animIn)
    }

    private fun initClickListener() {
        binding.edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.scrollView.smoothScrollTo(0, 1000)
            }
        }
        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
            val animOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
            binding.ivRegist.startAnimation(animOut)
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }
}