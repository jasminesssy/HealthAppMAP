package com.example.healthappmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        val btnBackToDashboard: Button = findViewById(R.id.btnBackToDashboard)
        btnBackToDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

    }
}
