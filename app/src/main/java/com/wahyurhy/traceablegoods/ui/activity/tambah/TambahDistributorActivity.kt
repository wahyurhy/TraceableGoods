package com.wahyurhy.traceablegoods.ui.activity.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDistributorBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahDistributorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDistributorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDistributorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}