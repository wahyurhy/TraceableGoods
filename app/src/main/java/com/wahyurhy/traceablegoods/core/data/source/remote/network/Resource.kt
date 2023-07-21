package com.wahyurhy.traceablegoods.core.data.source.remote.network

data class Resource<out T>(val state: State, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(State.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Resource<T> {
            return Resource(State.ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(State.LOADING, data, null)
        }

    }
}