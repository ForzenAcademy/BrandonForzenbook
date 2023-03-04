package com.brandon.utilities

import android.net.ConnectivityManager
import android.text.Editable
import android.view.View.FOCUSABLE
import android.view.View.NOT_FOCUSABLE
import com.google.android.material.textfield.TextInputEditText

fun ConnectivityManager.isConnected(): Boolean = activeNetworkInfo?.isConnected() ?: false

fun String.isNumeric(): Boolean = this.all { Character.isDigit(it.code) }