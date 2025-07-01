package com.example.enrollmentandregistration

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MenuInterface : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_interface)
        enableEdgeToEdge()

        // link menu buttons
        val registrationBtn = findViewById<Button>(R.id.registrationButton);
        val enrollmentBtn = findViewById<Button>(R.id.enrollmentButton);
        val feeBtn = findViewById<Button>(R.id.feeButton);

        registrationBtn.setOnClickListener {
            var intent = Intent()
            // change interface depending on the status of the logged in student
            when (StudentData.status.toString()) {
                "unregistered" -> {
                    intent = Intent(this, RegistrationInterface::class.java)
                    startActivity(intent)
                }
                "registered" -> {
                    intent = Intent(this, InterfaceHandler::class.java)
                    startActivity(intent)
                }
                "enrolled" -> {
                    AlertDialog.Builder(this)
                        .setTitle("NOTICE")
                        .setMessage("You are already enrolled!")
                        .setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
        enrollmentBtn.setOnClickListener {
            var intent = Intent()
            // change interface depending on the status of the logged in student
            when (StudentData.status.toString()) {
                "unregistered" -> {
                    AlertDialog.Builder(this)
                        .setTitle("NOTICE")
                        .setMessage("Please register first!")
                        .setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
                "registered" -> {
                    intent = Intent(this, EnrollmentInterface::class.java)
                    startActivity(intent)
                }
                "enrolled" -> {
                    intent = Intent(this, InterfaceHandler::class.java)
                    startActivity(intent)
                }
            }
        }
        feeBtn.setOnClickListener {
            findViewById<Button>(R.id.feeButton).setOnClickListener {
                Toast.makeText(this, "NO FEES FUNCTION YET", Toast.LENGTH_SHORT).show()
            }
//            var intent = Intent()
//            // change interface depending on the status of the logged in student
//            when (StudentData.status.toString()) {
//                "unregistered" -> {
//                    AlertDialog.Builder(this)
//                        .setTitle("NOTICE")
//                        .setMessage("Please register first!")
//                        .setNegativeButton("OK") { dialog, _ ->
//                            dialog.dismiss()
//                        }
//                        .show()
//                }
//                "registered" -> {
//                    intent = Intent(this, EnrollmentInterface::class.java)
//                    startActivity(intent)
//                }
//                "enrolled" -> {
//                    intent = Intent(this, InterfaceHandler::class.java)
//                    startActivity(intent)
//                }
//            }
        }
    }
}