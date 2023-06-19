package com.wahyurhy.traceablegoods.model.datainfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("id")
    var id: Int,
    @SerializedName("items")
    var items: List<Item>,
    @SerializedName("title")
    var title: String
): Parcelable