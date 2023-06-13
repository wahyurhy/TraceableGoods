package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataTengkulakBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahTengkulakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataTengkulakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataTengkulakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}