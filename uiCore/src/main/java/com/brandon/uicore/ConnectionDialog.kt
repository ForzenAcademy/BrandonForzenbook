package com.brandon.uicore

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

fun connectionDialog(context: Context) {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.connection_dialog, null)
    val dialog = AlertDialog.Builder(context).apply {
        setTitle(R.string.core_error_no_internet_connection)
        setMessage(R.string.core_error_connect_and_try_again)

    }
}