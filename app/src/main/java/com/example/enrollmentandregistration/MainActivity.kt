package com.example.enrollmentandregistration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add students
        val students = listOf(
            Student("20231000237", "", "Password123!", "unregistered", emptyList()),
            Student("admin2", "", "admin2", "unregistered", emptyList()),
            Student("admin3", "", "admin3", "unregistered", emptyList())
        )


        // !!! RUN THIS CODE TO OVERWRITE/RESET ALL RECORDS IN THE DATA FILE !!!
//
//        // convert to json then put in data.txt
//        val gson = Gson()
//        val jsonString = gson.toJson(students)
//        openFileOutput("data.txt", Context.MODE_PRIVATE).use {
//            it.write(jsonString.toByteArray())
//        }

        // !!! END OF "THIS CODE" !!!


        // link input fields and login button
        val studentNoField = findViewById<EditText>(R.id.studentno);
        val passwordField = findViewById<EditText>(R.id.password);
        val loginButton = findViewById<Button>(R.id.loginButton);

        loginButton.setOnClickListener {
            // open data file and put it in a list
            val gson = Gson()
            val json = openFileInput("data.txt").bufferedReader().readText()
            val type = object : TypeToken<List<Student>>() {}.type
            val studentList: List<Student> = gson.fromJson(json, type)

            // checks if a student with the inputted credentials is in the array
            var found = studentList.any {
                it.studentNo == studentNoField.text.toString() && it.password == passwordField.text.toString()
            }

            if (found) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                val chosenStudent = studentList.find { it.studentNo == studentNoField.text.toString()}

                // add logged in student details in StudentData.kt
                if (chosenStudent != null) {
                    StudentData.studentNo = chosenStudent.studentNo.toString()
                    StudentData.name = chosenStudent.name.toString()
                    StudentData.status = chosenStudent.status.toString()
                    StudentData.subjects = chosenStudent.subjects
                } else {
                    Toast.makeText(this, "No student found!", Toast.LENGTH_SHORT).show()
                }

                // change screens
                val intent = Intent(this, MenuInterface::class.java)
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}