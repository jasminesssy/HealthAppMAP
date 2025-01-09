package com.example.healthappmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.healthappmap.SleepTrackerActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnActivityTracker: Button = findViewById(R.id.btnActivityTracker)
        val btnSleepTracker: Button = findViewById(R.id.btnSleepTracker)
        val btnDrinkReminder: Button = findViewById(R.id.btnDrinkReminder)
        btnActivityTracker.setOnClickListener {
            startActivity(Intent(this, ActivityTrackerActivity::class.java))
        }

        btnSleepTracker.setOnClickListener {
            startActivity(Intent(this, SleepTrackerActivity::class.java))
        }

        btnDrinkReminder.setOnClickListener {
            startActivity(Intent(this, DrinkReminderActivity::class.java))
        }
    }
}
