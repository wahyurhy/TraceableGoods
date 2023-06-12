package com.wahyurhy.traceablegoods.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailDistributorBinding
import com.wahyurhy.traceablegoods.databinding.ActivityDetailGudangBinding
import com.wahyurhy.traceablegoods.databinding.ActivityDetailPengepulBinding
import com.wahyurhy.traceablegoods.databinding.ActivityDetailPenggilingBinding
import com.wahyurhy.traceablegoods.databinding.ActivityDetailTengkulakBinding
import com.wahyurhy.traceablegoods.ui.fragment.MasterDataFragment
import com.wahyurhy.traceablegoods.utils.Utils

class DetailTengkulakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTengkulakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTengkulakBinding.inflate(layoutInflater)
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