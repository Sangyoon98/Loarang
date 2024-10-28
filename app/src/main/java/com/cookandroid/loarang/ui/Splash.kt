package com.cookandroid.loarang.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cookandroid.loarang.MainActivity
import com.cookandroid.loarang.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(1500L)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
