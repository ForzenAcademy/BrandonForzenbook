package com.brandon.utilities

import android.net.ConnectivityManager
import android.text.Editable
import android.text.InputType.TYPE_NULL
import android.view.View.FOCUSABLE
import android.view.View.NOT_FOCUSABLE
import android.widget.EditText

fun ConnectivityManager.isConnected(): Boolean = activeNetworkInfo?.isConnected() ?: false

fun String.isNumeric(): Boolean = this.all { Character.isDigit(it.code) }

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun EditText.disable(): Unit {
    this.isEnabled = false
    this.isCursorVisible = false
    this.inputType = TYPE_NULL
    if (android.os.Build.VERSION.SDK_INT >= 26) {
        this.focusable = NOT_FOCUSABLE
    }
}

fun EditText.enable(inputTypeInt: Int) {
    this.isEnabled = true
    this.isCursorVisible = true
    this.inputType = inputTypeInt
    if (android.os.Build.VERSION.SDK_INT >= 26) {
        this.focusable = FOCUSABLE
    }
}