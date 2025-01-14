package com.example.healthappmap

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UpdateUserActivity : AppCompatActivity(){
    data class UserModel(
        val Id: String? = null,
        val Email: String? = null,
        val Name: String? = null,
        val Password: String? = null,
    )
    lateinit var btnUpdate : Button
    lateinit var etEmail : EditText
    lateinit var etName : EditText
    lateinit var etPassword : EditText
    lateinit var etPasswordConfirmation : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        btnUpdate = findViewById(R.id.btn_update)
        etEmail = findViewById(R.id.et_update_email)
        etName = findViewById(R.id.et_update_name)
        etPassword = findViewById(R.id.et_update_password)
        etPasswordConfirmation = findViewById(R.id.et_update_password_confirmation)

        btnUpdate.setOnClickListener {
            var userModel = UserModel(
                Email = etEmail.text.toString(),
                Name = etName.text.toString(),
                Password = PasswordHelper.md5(etPassword.text.toString())
            )
            this.updateUser(userModel)

        }
    }
    fun updateUser(userModel: UpdateUserActivity.UserModel) {
        val db = Firebase.firestore
        db.collection("user")
            .add(userModel)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    applicationContext,
                    "Berhasil melakukan update!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}