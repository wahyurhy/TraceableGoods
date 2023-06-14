package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPenerimaBinding
import com.wahyurhy.traceablegoods.ui.activity.TahapAlurDistribusiActivity
import com.wahyurhy.traceablegoods.utils.Utils

class TahapPenerimaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTahapPenerimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPenerimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()

        initClickListener()
    }

    private fun initClickListener() {
        binding.btnSelesai.setOnClickListener {
            startActivity(Intent(this, TahapAlurDistribusiActivity::class.java))
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}