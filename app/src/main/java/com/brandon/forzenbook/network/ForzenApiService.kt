package com.brandon.forzenbook.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForzenApiService {

    @POST(LOGIN_ENDPOINT)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST(CREATE_USER_ENDPOINT)
    suspend fun createUser(
        @Body request: CreateAccountRequest
    ): Response<Any>

    suspend fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    companion object {
        const val FORZEN_BASE_URL = "https://forzen.dev/api/"
        const val LOGIN_ENDPOINT = "login"
        const val CREATE_USER_ENDPOINT = "createUser"
    }
}