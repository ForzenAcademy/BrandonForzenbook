package com.brandon.utilities

import android.net.ConnectivityManager

fun ConnectivityManager.isConnected(): Boolean = activeNetworkInfo?.isConnected() ?: false
