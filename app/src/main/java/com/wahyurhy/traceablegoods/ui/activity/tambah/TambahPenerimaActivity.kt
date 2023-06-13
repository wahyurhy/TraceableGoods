package com.wahyurhy.traceablegoods.ui.activity.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahPenerimaBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahPenerimaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahPenerimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahPenerimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}