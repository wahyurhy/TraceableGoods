package com.wahyurhy.traceablegoods.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiAdapter
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiPenerimaAdapter
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiProdusenAdapter
import com.wahyurhy.traceablegoods.databinding.ActivityTahapAlurDistribusiBinding
import com.wahyurhy.traceablegoods.model.alurdistribusi.AlurDistribusiModel
import com.wahyurhy.traceablegoods.model.alurdistribusi.Result
import com.wahyurhy.traceablegoods.utils.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

class TahapAlurDistribusiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTahapAlurDistribusiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapAlurDistribusiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val batchId = intent.getStringExtra("batch_id")

        fitStatusBar()

        rawListInit(batchId)
    }

    private fun rawListInit(batchId: String?) {
        val gson = Gson()
        val i = assets.open("alur-distribusi.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, AlurDistribusiModel::class.java)

        bindData(dataList, batchId)
    }

    private fun bindData(dataList: AlurDistribusiModel, batchId: String?) {
        dataList.result.forEach { result ->
            if (result.batchId == batchId) {
                binding.batchId.text = result.batchId
                alurDistribusiProdusen(result)
                alurDistribusi(result)
                alurDistribusiPenerima(result)
            }
        }
    }

    private fun alurDistribusiPenerima(result: Result) {
        val penerima = result.record.size - 1
        val tahap = result.record[penerima].tahap
        val adapter = AlurDistribusiPenerimaAdapter(result.record[penerima].detail, tahap)
        binding.rvAlurPenerima.adapter = adapter
        adapter.setOnClickedListener(object :
            AlurDistribusiPenerimaAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val produkBatch = result.record[position].detail.produkBatch
                Toast.makeText(
                    this@TahapAlurDistribusiActivity,
                    "$produkBatch was clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun alurDistribusi(result: Result) {
        val adapter = AlurDistribusiAdapter(result.record.drop(1).dropLast(1))
        binding.rvAlurDistribusi.adapter = adapter
        adapter.setOnClickedListener(object :
            AlurDistribusiAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val produkBatch = result.record[position].detail.produkBatch
                Toast.makeText(
                    this@TahapAlurDistribusiActivity,
                    "$produkBatch was clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun alurDistribusiProdusen(result: Result) {
        val produsen = 0
        val tahap = result.record[produsen].tahap
        val adapter = AlurDistribusiProdusenAdapter(result.record[produsen].detail, tahap)
        binding.rvAlurProdusen.adapter = adapter
        adapter.setOnClickedListener(object :
            AlurDistribusiProdusenAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val produkBatch = result.record[position].detail.produkBatch
                Toast.makeText(
                    this@TahapAlurDistribusiActivity,
                    "$produkBatch was clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}