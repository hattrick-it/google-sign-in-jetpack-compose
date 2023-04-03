package com.hattrick.googlesignin.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.hattrick.googlesignin.presentation.compose.LoginScreen

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(onLoginSuccess = { onLoginSuccess() })
        }
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}