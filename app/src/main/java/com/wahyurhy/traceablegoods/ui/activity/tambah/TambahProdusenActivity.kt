package com.wahyurhy.traceablegoods.ui.activity.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahProdusenBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahProdusenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahProdusenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
        initClickListener()
    }

    private fun initClickListener() {

    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}