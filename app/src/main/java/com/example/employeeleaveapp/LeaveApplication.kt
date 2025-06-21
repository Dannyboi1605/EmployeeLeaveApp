package com.example.employeeleaveapp

data class LeaveApplication(
    val id: Int,
    val employeeName: String,
    val leaveType: String,
    val fromDate: String,
    val toDate: String,
    val reason: String,
    var status: String = "Pending", // Pending, Approved, Rejected
    var managerComment: String = ""
)
