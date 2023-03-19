package com.brandon.loginfragments.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brandon.loginfragments.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layout)
    }
}