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
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson() // creates a new instance of the gson class which is needed to manipulate json data like reading, writing and appending

        // create predefined students for initial run
        val students = listOf(
            Student("20231000237", "", "Password123!", "unregistered", emptyList()),
            Student("20231000045", "", "hellopass1", "unregistered", emptyList()),
            Student("20231000318", "", "miyagi", "unregistered", emptyList()),
            Student("1", "", "password", "unregistered", emptyList())
        )

        // check is there is already an existing data file, if none, create one
        try {
            openFileInput("data.txt").bufferedReader().readText() // if existing, open the file
        } catch (e: FileNotFoundException) {
            // if not existing, put in the predefined students
            val jsonString = gson.toJson(students) // put the predefined students in a json string
            openFileOutput("data.txt", Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray()) // overwrite the data file with the predefined students in it
            }
        }

        // !!! RUN THIS CODE TO OVERWRITE/RESET ALL RECORDS IN THE DATA FILE !!!

        // convert to json then put in data.txt
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
            // open data file
            val json = openFileInput("data.txt").bufferedReader().readText() // open file and read as json
            val type = object : TypeToken<List<Student>>() {}.type // define type to be converted into (in this case, list)
            val studentList: List<Student> = gson.fromJson(json, type) // convert to said type, and put it in a list

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