package com.wahyurhy.traceablegoods.model.alurdistribusi


import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("detail")
    var detail: Detail,
    @SerializedName("status")
    var status: String,
    @SerializedName("tahap")
    var tahap: String
)