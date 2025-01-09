package com.example.healthappmap

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.Calendar

class DrinkReminderActivity : AppCompatActivity() {
    private lateinit var btnDrink: Button
    private lateinit var tvDrinkCount: TextView
    private var drinkCount = 0
    private val NOTIFICATION_PERMISSION_CODE = 123
    private val CHANNEL_ID = "drink_reminder_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_reminder)

        setupViews()
        createNotificationChannel()
        checkNotificationPermission()
        scheduleReminders()
    }

    private fun setupViews() {
        btnDrink = findViewById(R.id.btnDrinkDone)
        tvDrinkCount = findViewById(R.id.tvDrinkCount)

        val btnBackToDashboard: Button = findViewById(R.id.btnBackToDashboard)
        btnBackToDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        btnDrink.setOnClickListener {
            if (btnDrink.text == "Drink") {
                drinkCount++
                tvDrinkCount.text = "Drink Count: $drinkCount"
                btnDrink.text = "Done"

                // Change text back to "Drink" after 2 seconds
                btnDrink.postDelayed({
                    btnDrink.text = "Drink"
                }, 2000)
            }
        }

    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Drink Reminder"
            val descriptionText = "Channel for drink reminder notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleReminders() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, DrinkReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule alarm every 2 hours
        val interval = 2 * 1000L // 2 hours in milliseconds
        val startTime = Calendar.getInstance().timeInMillis
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            startTime,
            interval,
            pendingIntent
        )
    }
}

// Create this as a separate file: DrinkReminderReceiver.kt
class DrinkReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationBuilder = NotificationCompat.Builder(context, "drink_reminder_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Drink Reminder")
            .setContentText("Time to drink water!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }
}