package com.example.healthappmap

import DrinkReminderActivity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnActivityTracker: Button = findViewById(R.id.btnActivityTracker)
        val btnSleepTracker: Button = findViewById(R.id.btnSleepTracker)
        val btnDrinkReminder: Button = findViewById(R.id.btnDrinkReminder)
        val btnUpdateProfile: Button = findViewById(R.id.btnUpdateProfile)
        val btnDeleteProfile: Button = findViewById(R.id.btnDeleteProfile)
        btnActivityTracker.setOnClickListener {
            startActivity(Intent(this, ActivityTrackerActivity::class.java))
        }

        btnSleepTracker.setOnClickListener {
            startActivity(Intent(this, SleepTrackerActivity::class.java))
        }

        btnDrinkReminder.setOnClickListener {
            startActivity(Intent(this, DrinkReminderActivity::class.java))
        }

        btnUpdateProfile.setOnClickListener {
            startActivity(Intent(this, UpdateUserActivity::class.java))
        }

        btnDeleteProfile.setOnClickListener {
            val email = intent.getStringExtra("email")


            if (email.isNullOrEmpty() ) {
                Toast.makeText(this, "Email or password is missing!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            deleteUser(email)
        }


    }
    fun deleteUser(email: String) {
        val db = Firebase.firestore
        db.collection("user")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                for (document in documents) {


                        db.collection("user").document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile deleted successfully!", Toast.LENGTH_SHORT).show()


                                val intent = Intent(this, SignInActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error deleting user document", e)
                                Toast.makeText(this, "Failed to delete profile. Try again.", Toast.LENGTH_SHORT).show()
                            }
                        return@addOnSuccessListener

                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error querying user document", e)
                Toast.makeText(this, "Failed to find user. Try again.", Toast.LENGTH_SHORT).show()
            }
    }

}
