package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTahapProdusenBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TahapProdusenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTahapProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapProdusenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}