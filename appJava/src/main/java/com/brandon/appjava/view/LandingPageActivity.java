package com.brandon.appjava.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.brandon.appjava.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }
}