package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPenggilingBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahPenggilingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataPenggilingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPenggilingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}