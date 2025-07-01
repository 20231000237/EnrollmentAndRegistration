package com.example.enrollmentandregistration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EnrollmentInterface : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_enrollment_interface)

        // display the details
        findViewById<TextView>(R.id.studentno).text = "${StudentData.studentNo}"
        findViewById<TextView>(R.id.name).text = "${StudentData.name}"

        // subject list
        val subjectListView = findViewById<ListView>(R.id.subjects)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, StudentData.subjects)
        subjectListView.adapter = adapter

        findViewById<Button>(R.id.enroll).setOnClickListener {
            AlertDialog.Builder(this) // create a dialogue for confirmation
                .setTitle("Enrollment Confirmation")
                .setMessage("Enroll the with the registered subjects?")
                .setPositiveButton("YES") { dialog, _ -> // we use lamda function because .setPositiveButton expects a .onClickListener (which is a function) // parameters: "dialogue" to use .dismiss function && "_" to ignore the other parameter (which parameter) *we dont use the which parameter since .setPositiveButton and .setNegativeButton has their own block

                    // read data file
                    val gson = Gson() // creates a new instance of the gson class which is needed for us to manipulate json data like reading, writing and appending
                    val json = openFileInput("data.txt").bufferedReader().readText() // open file and read as string
                    val type = object : TypeToken<MutableList<Student>>() {}.type // define type to be converted into (in this case, list)
                    val studentList: MutableList<Student> = gson.fromJson(json, type) // convert to said type

                    // find current logged in student in data file
                    val studentToUpdate = studentList.find { it.studentNo == StudentData.studentNo }

                    // update the student's status (registered -> enrolled)
                    if (studentToUpdate != null) {
                        val updatedStudent = studentToUpdate.copy(status = "enrolled")
                        val index = studentList.indexOf(studentToUpdate)
                        studentList[index] = updatedStudent

                        // update data in studentData
                        StudentData.status = "enrolled"
                    }

                    // overwrite the data file with the updated student subjects
                    val updatedJson = gson.toJson(studentList)
                    openFileOutput("data.txt", Context.MODE_PRIVATE).use {
                        it.write(updatedJson.toByteArray())
                    }

                    // display success toast message
                    Toast.makeText(this, "Successfully enrolled!", Toast.LENGTH_SHORT).show()

                    // change screens
                    val intent = Intent(this, InterfaceHandler::class.java)
                    startActivity(intent)
                    finish()

                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

        }

        // back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }
    }
}