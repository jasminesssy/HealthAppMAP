package com.example.healthappmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.healthappmap.DashboardActivity
import com.example.healthappmap.R

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btnLogin: Button = findViewById(R.id.btnSignInLogin)
        btnLogin.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}
