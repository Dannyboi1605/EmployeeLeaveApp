package com.example.employeeleaveapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leave_applications")
data class LeaveApplicationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeName: String,
    val leaveType: String,
    val fromDate: String,
    val toDate: String,
    val reason: String,
    val status: String = "Pending",
    val managerComment: String = ""
)
