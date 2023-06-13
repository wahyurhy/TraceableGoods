package com.wahyurhy.traceablegoods.ui.activity.tambah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.databinding.ActivityTambahPabrikPengolahanBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TambahPabrikPengolahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahPabrikPengolahanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahPabrikPengolahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}