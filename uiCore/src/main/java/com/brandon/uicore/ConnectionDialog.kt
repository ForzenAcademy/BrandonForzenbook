package com.brandon.uicore

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView

fun connectionDialog(context: Context) {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.connection_dialog, null)
    val dialog = AlertDialog.Builder(context).create()
    view.findViewById<TextView>(R.id.dialog_dismiss_button).setOnClickListener {
        dialog.dismiss()
    }
}