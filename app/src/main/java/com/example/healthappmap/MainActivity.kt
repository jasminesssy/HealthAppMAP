package com.example.healthappmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.healthappmap.RegisterActivity
import com.example.healthappmap.SignInActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignIn: Button = findViewById(R.id.btnSignIn)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
