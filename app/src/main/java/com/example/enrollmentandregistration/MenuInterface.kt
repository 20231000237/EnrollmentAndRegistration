package com.example.enrollmentandregistration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuInterface : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_interface)

        // link menu buttons
        val registrationBtn = findViewById<Button>(R.id.registrationButton);
        val enrollmentBtn = findViewById<Button>(R.id.enrollmentButton);
        val feeBtn = findViewById<Button>(R.id.feeButton);

        registrationBtn.setOnClickListener {
            var intent = Intent()
            when (StudentData.status.toString()) {
                "unregistered" -> intent = Intent(this, RegistrationInterface::class.java)
                "registered" -> intent = Intent(this, AlreadyRegisteredOrEnrolled::class.java)
                "enrolled" -> intent = Intent(this, AlreadyRegisteredOrEnrolled::class.java)
            }
            startActivity(intent)
        }
        enrollmentBtn.setOnClickListener {

        }
        feeBtn.setOnClickListener {

        }

    }
}