package com.brandon.uicore

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.brandon.legacycore.R

fun connectionDialog(context: Context, titleText: String, bodyText: String, onDismiss: () -> Unit = {}) {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.notification_dialog, null)
    val dialog = AlertDialog.Builder(context).create()
    view.findViewById<TextView>(R.id.title_text).text = titleText
    view.findViewById<TextView>(R.id.body_text).text = bodyText
    view.findViewById<TextView>(R.id.dialog_dismiss_button).setOnClickListener {
        dialog.dismiss()
        onDismiss()
    }
    dialog.show()
}