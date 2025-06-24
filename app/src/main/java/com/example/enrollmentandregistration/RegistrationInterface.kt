package com.example.enrollmentandregistration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.toByteArray

class RegistrationInterface : AppCompatActivity() {
    lateinit var nameField: EditText
    lateinit var selectionList: ListView
    lateinit var registerButton: Button

    val subjectArray = arrayOf(
        Subject(
            code = "GED0081",
            name = "PHYSICS 1",
            units = 3,
            prof = "Ms. Rizal",
            sched = "M - 13:00-15:40"
        ),
        Subject(
            code = "GED0031",
            name = "Purposive Communication",
            units = 3,
            prof = "Mr. John",
            sched = "M - 9:00-10:50"
        ),
        Subject(
            code = "IT0063",
            name = "Advanced Web Design",
            units = 3,
            prof = "Ms. Domingo",
            sched = "T - 7:00-10:00"
        ),
        Subject(
            code = "CCS0101",
            name = "Design Thinking",
            units = 3,
            prof = "Mr. Ross",
            sched = "T - 11:00-13:50"
        ),
        Subject(
            code = "CCS0103",
            name = "Technopreneurship",
            units = 3,
            prof = "Ms. Beth",
            sched = "W - 10:00-12:50"
        ),
        Subject(
            code = "IT0069",
            name = "Fundamentals of Analytics",
            units = 3,
            prof = "Mr. Allen",
            sched = "W - 13:00-15:50"
        ),
        Subject(
            code = "GED0019",
            name = "Understanding the Self",
            units = 3,
            prof = "Mr. Juan",
            sched = "W - 16:00-17:50"
        ),
        Subject(
            code = "GED0039",
            name = "Applied Statistics",
            units = 3,
            prof = "Ms. Doe",
            sched = "TH - 9:00-10:50"
        ),
        Subject(
            code = "IT0013",
            name = "Networking 1",
            units = 3,
            prof = "Mr. Wayne",
            sched = "TH - 11:00-13:50"
        ),
        Subject(
            code = "IT0017",
            name = "Discrete Mathematics",
            units = 3,
            prof = "Ms. Jackie",
            sched = "F - 11:00-13:40"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_interface)

        // display status of currently logged in student
        val status = findViewById<TextView>(R.id.status)
        status.text = "Status: ${StudentData.status}"

        // link elements from the .xml file
        nameField = findViewById<EditText>(R.id.name)
        selectionList = findViewById(R.id.subjectList)
        registerButton = findViewById(R.id.register)

        //adapter gets content of subject array to create items of list view based on the content
        //"this" refer the current activity
        //"android.R.layout.simple..." is a built in layout that makes each item on the list view have a checkbox to allow multiple selections
        //"subjectArray" is the source of data to be adapted
        val adapter = ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_multiple_choice, subjectArray)

        //All 5 lines displays properly on emulator yet cuts off on real phone test
        //list_item_subject_selection is custom layout xml file to guarantee no cutoff
        //val adapter = ArrayAdapter<Subject>(this, R.layout.list_item_subject_selection, subjectArray)

        //implements/attaches the adapter into the list view
        selectionList.adapter = adapter

        registerButton.setOnClickListener {
            //created list to store selected subjects
            val selectedSubjects = mutableListOf<Subject>()
            //for loop to go through all list items
            for (i in 0 until selectionList.count) {
                //checks each list item if it is checked
                if (selectionList.isItemChecked(i)) {
                    //added to selectedSubjects list
                    selectedSubjects.add(selectionList.getItemAtPosition(i) as Subject)
                }
            }

            if (selectedSubjects.isEmpty()) {
                Toast.makeText(this, "Please select at least one subject!", Toast.LENGTH_SHORT).show()
            } else if (nameField.text.toString().isBlank()) {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            } else {
                // update data in StudentData
                StudentData.subjects = selectedSubjects
                StudentData.status = "registered"
                StudentData.name = nameField.text.toString()

                // read data file
                val gson = Gson() // creates a new instance of the gson class which is needed for us to manipulate json data like reading, writing and appending
                val json = openFileInput("data.txt").bufferedReader().readText() // open file and read as json
                val type = object : TypeToken<MutableList<Student>>() {}.type // define type to be converted into (in this case, list)
                val studentList: MutableList<Student> = gson.fromJson(json, type) // convert to said type, and put it in a list

                // find current logged in student in data file
                val studentToUpdate = studentList.find { it.studentNo == StudentData.studentNo }

                // update the subjects by updating the following:
                // selected subjects list
                // status (unregistered -> registered)
                // name (none -> entered name)
                if (studentToUpdate != null) {
                    val updatedStudent = studentToUpdate.copy(subjects = selectedSubjects, status = "registered", name = nameField.text.toString())
                    val index = studentList.indexOf(studentToUpdate)
                    studentList[index] = updatedStudent
                }

                // overwrite the data file with the updated student subjects
                val updatedJson = gson.toJson(studentList)
                openFileOutput("data.txt", Context.MODE_PRIVATE).use {
                    it.write(updatedJson.toByteArray())
                }

                // change screens
                val intent = Intent(this, AlreadyRegisteredOrEnrolled::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}