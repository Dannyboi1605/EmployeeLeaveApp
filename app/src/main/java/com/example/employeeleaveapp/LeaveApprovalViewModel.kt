package com.example.employeeleaveapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LeaveApprovalViewModel(private val dao: LeaveApplicationDao) : ViewModel() {

    val applications: StateFlow<List<LeaveApplicationEntity>> =
        dao.getAllLeaveApplications().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun approveLeave(app: LeaveApplicationEntity, comment: String) {
        viewModelScope.launch {
            dao.updateLeaveApplication(
                app.copy(status = "Approved", managerComment = comment)
            )
        }
    }

    fun rejectLeave(app: LeaveApplicationEntity, comment: String) {
        viewModelScope.launch {
            dao.updateLeaveApplication(
                app.copy(status = "Rejected", managerComment = comment)
            )
        }
    }

    fun addTestData() {
        viewModelScope.launch {
            dao.insertLeaveApplication(
                LeaveApplicationEntity(
                    employeeName = "Ahmad",
                    leaveType = "Sick Leave",
                    fromDate = "2025-06-20",
                    toDate = "2025-06-21",
                    reason = "Flu"
                )
            )
            dao.insertLeaveApplication(
                LeaveApplicationEntity(
                    employeeName = "Aisyah",
                    leaveType = "Casual Leave",
                    fromDate = "2025-06-25",
                    toDate = "2025-06-26",
                    reason = "Family trip"
                )
            )
        }
    }
}

class LeaveApprovalViewModelFactory(private val dao: LeaveApplicationDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LeaveApprovalViewModel(dao) as T
    }
}
