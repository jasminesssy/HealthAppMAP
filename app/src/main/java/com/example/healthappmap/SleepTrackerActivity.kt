package com.example.healthappmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.TimeUnit

class SleepTrackerActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var tvSleepHours: TextView
    private lateinit var tvSleepQuality: TextView

    private var isTracking = false
    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())

    private val timerRunnable = object : Runnable {
        override fun run() {
            seconds++
            updateTimerDisplay()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_tracker)


        btnStart = findViewById(R.id.btnSleepStart)
        tvSleepHours = findViewById(R.id.tvSleepHours)
        tvSleepQuality = findViewById(R.id.tvSleepQuality)


        val btnBackToDashboard: Button = findViewById(R.id.btnBackToDashboard)
        btnBackToDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }


        btnStart.setOnClickListener {
            if (!isTracking) {

                startTracking()
            } else {

                stopTracking()
            }
        }
    }

    private fun startTracking() {
        isTracking = true
        btnStart.text = "Stop"
        handler.post(timerRunnable)
    }

    private fun stopTracking() {
        isTracking = false
        btnStart.text = "Start"
        handler.removeCallbacks(timerRunnable)
        analyzeSleepQuality()
    }

    private fun updateTimerDisplay() {
        val hours = TimeUnit.SECONDS.toHours(seconds.toLong())
        val minutes = TimeUnit.SECONDS.toMinutes(seconds.toLong()) % 60
        val secs = seconds % 60

        val timeString = String.format("%02d:%02d:%02d", hours, minutes, secs)
        tvSleepHours.text = "Sleep Duration: $timeString"
    }

    private fun analyzeSleepQuality() {
        val hours = TimeUnit.SECONDS.toHours(seconds.toLong())
        val quality = when {
            hours >= 8 -> "Good sleep"
            hours >= 3 -> "Need more sleep"
            else -> "Sleep deprivation"
        }
        tvSleepQuality.text = "Sleep Quality: $quality"
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
    }
}