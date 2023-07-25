package com.wahyurhy.traceablegoods.core.data.repository

import android.util.Log
import com.wahyurhy.traceablegoods.core.data.source.remote.RemoteDataSource
import com.wahyurhy.traceablegoods.core.data.source.remote.network.Resource
import com.wahyurhy.traceablegoods.utils.Prefs
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class AppRepository(val remote: RemoteDataSource) {

    fun login(email: String, password: String) = flow {
        emit(Resource.loading(null))
        try {
            remote.login(email, password).let {
                if (it.isSuccessful) {
                    Prefs.isLogin = true
                    val data = it.body()?.data
                    emit(Resource.success(data))
                    Log.d("AppRepository", "Login Berhasil: ${it.body().toString()}")
                } else {
                    val errorBody = it.errorBody()?.string() ?: "Terjadi Error"
                    val errorMessage = JSONObject(errorBody).getString("message")
                    emit(Resource.error(errorMessage,null))
                    Log.d("AppRepository", errorMessage)
                }
            }
        } catch (e: Exception) {
            emit(Resource.error("Terjadi error ${e.message}", null))
            Log.d("AppRepository", "Login error: ${e.message}")
        }
    }

}