package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPabrikPengolahanBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahPabrikPengolahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDataPabrikPengolahanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPabrikPengolahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}