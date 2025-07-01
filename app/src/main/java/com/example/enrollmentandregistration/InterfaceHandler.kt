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
        setContentView(R.layout.activity_interface_handler)
        enableEdgeToEdge()

        // read data file
        val gson = Gson() // creates a new instance of the gson class which is needed for us to manipulate json data like reading, writing and appending
        val json = openFileInput("data.txt").bufferedReader().readText() // open file and read as a string
        val type = object : TypeToken<MutableList<Student>>() {}.type // define type to be converted into (in this case, list)
        val studentList: MutableList<Student> = gson.fromJson(json, type) // convert to said type

        // find current logged in student in data file
        val chosenStudent = studentList.find { it.studentNo == StudentData.studentNo }

        // display the details
        if (chosenStudent != null) {
            val status = findViewById<TextView>(R.id.status)
            findViewById<TextView>(R.id.studentno).text = "${chosenStudent.studentNo}"
            status.text = "${chosenStudent.status}"
            findViewById<TextView>(R.id.name).text = "${chosenStudent.name}"

            // change text color depending on the status
            if (status.text.toString() == "registered") {
                status.setTextColor(Color.parseColor("#008000"))
            } else if (status.text.toString() == "enrolled") {
                status.setTextColor(Color.parseColor("#0096FF"))
            } else {
                status.setTextColor(Color.parseColor("#8B0000"))
            }
            // subject list
            val subjectListView = findViewById<ListView>(R.id.subjects)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chosenStudent.subjects)
            subjectListView.adapter = adapter
        }

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
                Toast.makeText(this, "NO FEES FUNCTION YET", Toast.LENGTH_SHORT).show()
            }
        }

        // back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }
    }
}