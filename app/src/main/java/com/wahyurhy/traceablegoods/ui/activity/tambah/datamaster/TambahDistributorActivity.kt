package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataDistributorBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahDistributorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataDistributorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataDistributorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}