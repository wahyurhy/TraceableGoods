package com.wahyurhy.traceablegoods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wahyurhy.traceablegoods.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        rawListInit()
    }

//    private fun rawListInit() {
//        val gson = Gson()
//        val i = requireContext().assets.open("data_info.json")
//        val br = BufferedReader(InputStreamReader(i))
//        val dataList = gson.fromJson(br, DataInfoModel::class.java)
//
//        bindData(dataList)
//    }

//    private fun bindData(dataList: DataInfoModel) {
//        dataList.result.forEach { result ->
//            val adapter = DataInfoAdapter(result.items)
//            binding.rvDataInfo.adapter = adapter
//            adapter.setOnClickedListener(object : DataInfoAdapter.OnItemClickListener {
//                override fun onItemClick(itemView: View?, position: Int) {
//                    val dataName = result.items[position].dataName
//                    Toast.makeText(requireContext(), "$dataName was clicked!", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }

}