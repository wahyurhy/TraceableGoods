package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataProdusenBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahProdusenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataProdusenBinding.inflate(layoutInflater)
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