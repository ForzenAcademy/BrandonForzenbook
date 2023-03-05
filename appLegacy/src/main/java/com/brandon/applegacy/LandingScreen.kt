package com.brandon.applegacy

import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brandon.applegacy.R
import com.brandon.navigation.LegacyNavigation
import javax.inject.Inject

@AndroidEntryPoint
class LandingScreen : AppCompatActivity() {

    @Inject
    lateinit var legacyNavigation: LegacyNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_screen)
    }
}