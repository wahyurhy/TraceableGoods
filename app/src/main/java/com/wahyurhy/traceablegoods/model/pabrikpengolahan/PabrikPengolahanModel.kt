package com.wahyurhy.traceablegoods.model.pabrikpengolahan


import com.google.gson.annotations.SerializedName

data class PabrikPengolahanModel(
    @SerializedName("result")
    val result: List<Result>
)