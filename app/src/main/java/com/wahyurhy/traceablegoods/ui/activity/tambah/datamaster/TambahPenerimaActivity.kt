package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPenerimaBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahPenerimaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataPenerimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPenerimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}