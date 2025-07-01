package com.example.enrollmentandregistration

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FeeInterface : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fee_interface)

        findViewById<TextView>(R.id.studentno).text = StudentData.studentNo
        findViewById<TextView>(R.id.name).text = StudentData.name

        val subjectListView = findViewById<ListView>(R.id.subjects)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, StudentData.subjects)
        subjectListView.adapter = adapter

        // add misc fees and its corresponding list

        // compute for total fee




        // back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }
    }
}