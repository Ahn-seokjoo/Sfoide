package com.example.sfoide.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sfoide.databinding.UserDetailBinding

private lateinit var binding: UserDetailBinding

class UserDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val userData = Intent().getStringExtra("userData")
//        Log.d(TAG, "onCreate: $userData")
    }
}