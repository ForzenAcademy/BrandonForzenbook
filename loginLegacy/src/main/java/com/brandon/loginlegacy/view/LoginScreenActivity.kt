package com.brandon.loginlegacy.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brandon.loginlegacy.databinding.LoginScreenBinding

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var binding: LoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}