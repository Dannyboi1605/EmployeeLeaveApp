package com.example.employeeleaveapp

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

data class LeaveType(
    val leaveType: String,
    val role: String,
    val entitlement: Int,
    val accrualPolicy: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveTypeManagementScreen(onBack: () -> Unit) {
    val leaveOptions = listOf("Sick Leave", "Casual Leave", "Paid Leave")
    val roles = listOf("Employee", "Manager", "Admin")
    val roleEntitlements = mapOf(
        "Employee" to 10,
        "Manager" to 15,
        "Admin" to 20
    )
    val roleAccrualPolicies = mapOf(
        "Employee" to "Monthly",
        "Manager" to "Quarterly",
        "Admin" to "Yearly"
    )

    var selectedLeaveType by remember { mutableStateOf("") }
    var expandedLeave by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf("") }
    var expandedRole by remember { mutableStateOf(false) }

    var entitlement by remember { mutableStateOf("") }
    var accrualPolicy by remember { mutableStateOf("") }

    var leaveTypes by remember { mutableStateOf(listOf<LeaveType>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leave Type Management") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {

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
                                entitlement = roleEntitlements[role]?.toString() ?: ""
                                accrualPolicy = roleAccrualPolicies[role] ?: ""
                                expandedRole = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = entitlement,
                onValueChange = {},
                label = { Text("Entitlement (Days)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = accrualPolicy,
                onValueChange = {},
                label = { Text("Accrual Policy") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (selectedLeaveType.isNotBlank() && selectedRole.isNotBlank() &&
                        entitlement.isNotBlank() && accrualPolicy.isNotBlank()
                    ) {
                        leaveTypes = leaveTypes + LeaveType(
                            selectedLeaveType,
                            selectedRole,
                            entitlement.toInt(),
                            accrualPolicy
                        )
                        selectedLeaveType = ""
                        selectedRole = ""
                        entitlement = ""
                        accrualPolicy = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Leave Type")
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
                            Text("Entitlement: ${type.entitlement} days")
                            Text("Accrual Policy: ${type.accrualPolicy}")
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
    LeaveTypeManagementScreen(onBack = {})
}
