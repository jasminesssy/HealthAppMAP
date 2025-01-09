package com.example.healthappmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister: Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}
