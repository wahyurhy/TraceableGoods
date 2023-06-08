package com.wahyurhy.traceablegoods

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.adapter.DataInfoAdapter
import com.wahyurhy.traceablegoods.databinding.ActivityMainBinding
import com.wahyurhy.traceablegoods.model.DataInfoModel
import com.wahyurhy.traceablegoods.utils.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusbar()

        rawListInit()
    }

    private fun rawListInit() {
        val gson = Gson()
        val i = this.assets.open("data_info.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, DataInfoModel::class.java)

        bindData(dataList)
    }

    private fun bindData(dataList: DataInfoModel) {
        dataList.result.forEach { result ->
            val adapter = DataInfoAdapter(result.items)
            binding.rvDataInfo.adapter = adapter
            adapter.setOnClickedListener(object : DataInfoAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val dataName = result.items[position].dataName
                    Toast.makeText(this@MainActivity, "$dataName was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fitStatusbar() {
        Utils.setSystemBarFitWindow(this)
    }
}