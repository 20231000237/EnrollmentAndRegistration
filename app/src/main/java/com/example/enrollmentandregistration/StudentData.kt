package com.example.enrollmentandregistration

import javax.security.auth.Subject

// for current logged in student
object StudentData {
    var studentNo: String = ""
    var status: String = ""
    var name: String = ""
    var subjects: List<com.example.enrollmentandregistration.Subject> = emptyList()

}
