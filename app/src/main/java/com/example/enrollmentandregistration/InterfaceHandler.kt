package com.example.enrollmentandregistration

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InterfaceHandler : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_interface_handler)


        // display the details
        val status = findViewById<TextView>(R.id.status)
        findViewById<TextView>(R.id.studentno).text = "${StudentData.studentNo}"
        status.text = "${StudentData.status}"
        findViewById<TextView>(R.id.name).text = "${StudentData.name}"

        // change text color depending on the status
        if (status.text.toString() == "registered") {
            status.setTextColor(Color.parseColor("#008000")) // parseColor() to convert string to int since setTextColor expects int
        } else if (status.text.toString() == "enrolled") {
            status.setTextColor(Color.parseColor("#0096FF"))
        } else {
            status.setTextColor(Color.parseColor("#8B0000"))
        }
        // subject list
        val subjectListView = findViewById<ListView>(R.id.subjects)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, StudentData.subjects)
        subjectListView.adapter = adapter


        if (StudentData.status.toString() == "registered") { // if the student's status is "registered"
            findViewById<TextView>(R.id.title).text = "Registration Status"
            findViewById<TextView>(R.id.subjectListLabel).text = "Registered Subjects:"
            findViewById<Button>(R.id.toNextStep).text = "PROCEED TO ENROLLMENT"
            findViewById<Button>(R.id.toNextStep).setOnClickListener {
                val intent = Intent(this, EnrollmentInterface::class.java)
                startActivity(intent)
                finish()
            }
        } else if (StudentData.status.toString() == "enrolled") { // if the student's status is "enrolled"
            findViewById<TextView>(R.id.title).text = "Enrollment Status"
            findViewById<TextView>(R.id.subjectListLabel).text = "Enrolled Subjects:"
            findViewById<Button>(R.id.toNextStep).text = "PROCEED TO FEES"
            findViewById<Button>(R.id.toNextStep).setOnClickListener {
                val intent = Intent(this, FeeInterface::class.java)
                startActivity(intent)
                finish()
            }
        }

        // back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }
    }
}