package com.wahyurhy.traceablegoods.ui.activity.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataMasterBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahDataMasterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataMasterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}