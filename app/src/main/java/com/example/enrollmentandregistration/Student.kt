package com.example.enrollmentandregistration

data class Student(
    val studentNo: String,
    val name: String,
    val password: String,
    val status: String,
    val subjects: List<Subject>
)