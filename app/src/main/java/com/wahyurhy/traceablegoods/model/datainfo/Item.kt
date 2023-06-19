package com.wahyurhy.traceablegoods.model.datainfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("data_name")
    var dataName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("list_data")
    var listData: List<Data>
): Parcelable