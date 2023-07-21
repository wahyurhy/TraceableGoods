package com.wahyurhy.traceablegoods.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.wahyurhy.traceablegoods.MainActivity
import com.wahyurhy.traceablegoods.MainObserverActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.core.data.source.remote.network.State
import com.wahyurhy.traceablegoods.databinding.ActivityLoginBinding
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private var isAllSet = false

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
        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            isAllSet = email.isEmpty(binding.edtEmail, getString(R.string.email_tidak_kosong))
            isAllSet = password.isEmpty(binding.edtPassword, getString(R.string.password_tidak_kosong))

            if (isAllSet) {
                viewModel.login(email, password).observe(this) {
                    when (it.state) {
                        State.SUCCESS -> {
                            binding.loading.visibility = View.GONE
                            Snackbar.make(
                                binding.root,
                                getString(R.string.success_login, it.data?.nama),
                                Snackbar.LENGTH_SHORT
                            ).apply {
                                view.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
                                setActionTextColor(ContextCompat.getColor(this@LoginActivity, android.R.color.white))
                                val snackbarView = view
                                val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
                                params.bottomMargin = 100
                                snackbarView.layoutParams = params
                                show()
                            }
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                        State.ERROR -> {
                            binding.loading.visibility = View.GONE
                            Snackbar.make(
                                binding.root,
                                it.message ?: "Terjadi error",
                                Snackbar.LENGTH_SHORT
                            ).apply {
                                anchorView = binding.edtEmail
                                view.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.red))
                                setActionTextColor(ContextCompat.getColor(this@LoginActivity, android.R.color.white))
                                val snackbarView = view
                                val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
                                snackbarView.layoutParams = params
                                show()
                            }
                        }
                        State.LOADING -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        binding.edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.scrollView.smoothScrollTo(0, 1000)
            }
        }
        binding.btnObserver.setOnClickListener {
            startActivity(Intent(this, MainObserverActivity::class.java))
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }
}