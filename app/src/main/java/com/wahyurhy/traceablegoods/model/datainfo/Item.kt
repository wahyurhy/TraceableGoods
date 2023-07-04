package com.wahyurhy.traceablegoods.model.datainfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("id")
    var id: Int,
    @SerializedName("data_name")
    var dataName: String,
    @SerializedName("timestamp")
    var timestamp: String
): Parcelable