package com.example.groupassignment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

data class LeaveType(
    val leaveType: String,
    val role: String,
    val employeeName: String,
    val fromDate: String,
    val toDate: String,
    val reason: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveTypeManagementScreen(onBack: () -> Unit = {}) {
    val leaveOptions = listOf("Sick Leave", "Casual Leave", "Paid Leave")
    val roles = listOf("Employee", "Manager", "Admin")

    var selectedLeaveType by remember { mutableStateOf("") }
    var expandedLeave by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf("") }
    var expandedRole by remember { mutableStateOf(false) }

    var employeeName by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }

    val fromDatePickerState = rememberDatePickerState()
    val toDatePickerState = rememberDatePickerState()
    val showFromDatePicker = remember { mutableStateOf(false) }
    val showToDatePicker = remember { mutableStateOf(false) }

    var leaveTypes by remember { mutableStateOf(listOf<LeaveType>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leave Request") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Leave Type Dropdown
            ExposedDropdownMenuBox(
                expanded = expandedLeave,
                onExpandedChange = { expandedLeave = !expandedLeave }
            ) {
                TextField(
                    value = selectedLeaveType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Leave Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLeave)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedLeave,
                    onDismissRequest = { expandedLeave = false }
                ) {
                    leaveOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedLeaveType = option
                                expandedLeave = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Role Dropdown
            ExposedDropdownMenuBox(
                expanded = expandedRole,
                onExpandedChange = { expandedRole = !expandedRole }
            ) {
                TextField(
                    value = selectedRole,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Role") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRole)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedRole,
                    onDismissRequest = { expandedRole = false }
                ) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role) },
                            onClick = {
                                selectedRole = role
                                expandedRole = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Employee Name Field
            OutlinedTextField(
                value = employeeName,
                onValueChange = { employeeName = it },
                label = { Text("Employee Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (showFromDatePicker.value) {
                DatePickerDialog(
                    onDismissRequest = { showFromDatePicker.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            fromDatePickerState.selectedDateMillis?.let {
                                val cal = Calendar.getInstance().apply { timeInMillis = it }
                                fromDate =
                                    "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
                            }
                            showFromDatePicker.value = false
                        }) {
                            Text("OK")
                        }
                    }
                ) {
                    DatePicker(state = fromDatePickerState)
                }
            }

            if (showToDatePicker.value) {
                DatePickerDialog(
                    onDismissRequest = { showToDatePicker.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            toDatePickerState.selectedDateMillis?.let {
                                val cal = Calendar.getInstance().apply { timeInMillis = it }
                                toDate =
                                    "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
                            }
                            showToDatePicker.value = false
                        }) {
                            Text("OK")
                        }
                    }
                ) {
                    DatePicker(state = toDatePickerState)
                }
            }

            // From Date
            OutlinedTextField(
                value = fromDate,
                onValueChange = {},
                label = { Text("From Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showFromDatePicker.value = true },
                readOnly = true,
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // To Date
            OutlinedTextField(
                value = toDate,
                onValueChange = {},
                label = { Text("To Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showToDatePicker.value = true },
                readOnly = true,
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Reason
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (selectedLeaveType.isNotBlank() && selectedRole.isNotBlank()
                        && employeeName.isNotBlank()
                        && fromDate.isNotBlank() && toDate.isNotBlank() && reason.isNotBlank()
                    ) {
                        leaveTypes = leaveTypes + LeaveType(
                            selectedLeaveType, selectedRole, employeeName, fromDate, toDate, reason
                        )
                        selectedLeaveType = ""
                        selectedRole = ""
                        employeeName = ""
                        fromDate = ""
                        toDate = ""
                        reason = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Request Leave")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(leaveTypes) { type ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Leave Type: ${type.leaveType}")
                            Text("Role: ${type.role}")
                            Text("Name: ${type.employeeName}")
                            Text("From: ${type.fromDate}")
                            Text("To: ${type.toDate}")
                            Text("Reason: ${type.reason}")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaveTypeManagementScreen() {
    LeaveTypeManagementScreen()
}
