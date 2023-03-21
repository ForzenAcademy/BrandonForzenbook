package com.brandon.appfragments.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandon.appfragments.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layout)
    }
}