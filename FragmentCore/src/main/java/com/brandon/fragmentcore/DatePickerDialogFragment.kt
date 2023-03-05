package com.brandon.fragmentcore

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.brandon.uicore.R
import java.util.*

class DatePickerDialogFragment(
    private val onDatePicked: (Int, Int, Int) -> Unit,
    private val onOpen: () -> Unit = {},
    private val onDismiss: () -> Unit = {}
) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        onOpen()
        return DatePickerDialog(
            requireContext(),
            R.style.MySpinnerStyle,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun dismiss() {
        onDismiss()
        super.dismiss()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDatePicked(year, month, dayOfMonth)
    }
}