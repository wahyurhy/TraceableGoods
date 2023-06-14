package com.wahyurhy.traceablegoods.model.alurdistribusi


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("batch_id")
    var batchId: String,
    @SerializedName("record")
    var record: List<Record>
)