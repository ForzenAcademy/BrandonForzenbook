package com.brandon.fragmentcore

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.brandon.uicore.R

class NotificationDialogFragment(
    private val title: String,
    private val body: String,
    private val onOpen: () -> Unit = {},
    private val onConfirm: () -> Unit = {},
    private val onDismiss: () -> Unit = {},
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        onOpen()
        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton(getString(R.string.core_error_dismiss)) { _, _ ->
                onConfirm()
                this.dismiss()
            }
            .create()
    }

    override fun dismiss() {
        onDismiss()
        super.dismiss()
    }
}