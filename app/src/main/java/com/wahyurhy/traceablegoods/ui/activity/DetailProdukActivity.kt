package com.wahyurhy.traceablegoods.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailProdukBinding
import com.wahyurhy.traceablegoods.ui.fragment.MasterDataFragment
import com.wahyurhy.traceablegoods.utils.Utils

class DetailProdukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameDetail = intent.getStringExtra(MasterDataFragment.NAME_LIST) ?: ""

        initExtras(nameDetail)
        fitStatusBar()
    }

    private fun initExtras(nameList: String) {
        binding.tvDetailTitle.text = getString(R.string.list_title, nameList)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}