package com.brandon.uicore

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText

@SuppressLint("ClickableViewAccessibility")
class ForcibleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : EditText(context, attrs, defStyle) {

    override fun addTextChangedListener(watcher: TextWatcher?) {
        watcher?.let { if(!textListeners.contains(it)) textListeners.add(it) }
        super.addTextChangedListener(watcher)
    }

    private var textListeners = mutableListOf<TextWatcher>()

    private fun removeAllListeners() {
        textListeners.forEach { removeTextChangedListener(it) }
    }

    private fun addBackAllListenters() {
        textListeners.forEach { addTextChangedListener(it) }
    }

    fun forceText(s: String) {
        removeAllListeners()
        setText(s)
        addBackAllListenters()
    }
}