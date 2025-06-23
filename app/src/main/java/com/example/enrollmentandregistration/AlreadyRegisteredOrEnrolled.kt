package com.example.enrollmentandregistration

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlreadyRegisteredOrEnrolled : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_already_registered_or_enrolled)

        // read data file
        val gson = Gson()
        val type = object : TypeToken<MutableList<Student>>() {}.type
        val json = openFileInput("data.txt").bufferedReader().readText()
        val studentList: MutableList<Student> = gson.fromJson(json, type)

        // find current logged in student in data file
        val chosenStudent = studentList.find { it.studentNo == StudentData.studentNo }

        // display the details
        if (chosenStudent != null) {
            findViewById<TextView>(R.id.studentno).text = "${chosenStudent.studentNo}"
            findViewById<TextView>(R.id.status).text = "${chosenStudent.status}"
            findViewById<TextView>(R.id.name).text = "${chosenStudent.name}"

            // subject list
            val subjectListView = findViewById<ListView>(R.id.subjects)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chosenStudent.subjects)
            subjectListView.adapter = adapter
        }

        findViewById<Button>(R.id.toEnrollment).setOnClickListener {
            val intent = Intent(this, RegistrationInterface::class.java) // replace to enrollment interface when created
            startActivity(intent)
            finish()
        }


    }
}