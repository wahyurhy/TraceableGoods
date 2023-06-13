package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataProdukBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahProdukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataProdukBinding.inflate(layoutInflater)
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