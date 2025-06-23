package com.example.enrollmentandregistration

data class Subject(
    val code: String,
    val name: String,
    val units: Int,
    val prof: String,
    val sched: String
)
{
    //this function is called each time an subject object is used by the adapter
    //to turn each object into a formatted string for the listview
    override fun toString(): String {
        return "$name\n$code\nUnits: $units\n$sched\nProf: $prof"
    }
}