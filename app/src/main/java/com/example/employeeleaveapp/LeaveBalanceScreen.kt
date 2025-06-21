package com.example.employeeleaveapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

data class EmployeeLeaveBalance(
    val name: String,
    var leaveBalance: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveBalanceScreen(onBack: () -> Unit = {}) {
    // Hardcoded employee leave balances
    var employees by remember {
        mutableStateOf(
            listOf(
                EmployeeLeaveBalance("Alice", 15),
                EmployeeLeaveBalance("Bob", 28),
                EmployeeLeaveBalance("Charlie", 5)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leave Balance Management") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(employees) { employee ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(employee.name, style = MaterialTheme.typography.titleMedium)
                                Text("Balance: ${employee.leaveBalance} days")
                            }
                            Button(
                                onClick = {
                                    // Simulate leave approval: deduct 1 day if balance > 0
                                    if (employee.leaveBalance > 0) {
                                        val updatedList = employees.toMutableList()
                                        val index = updatedList.indexOf(employee)
                                        updatedList[index] =
                                            employee.copy(leaveBalance = employee.leaveBalance - 1)
                                        employees = updatedList
                                    }
                                },
                                enabled = employee.leaveBalance > 0
                            ) {
                                Text("Approve Leave")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                // Carry forward leaves, max balance capped at 30
                val updatedList = employees.map {
                    val newBalance = if (it.leaveBalance > 30) 30 else it.leaveBalance
                    it.copy(leaveBalance = newBalance)
                }
                employees = updatedList
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Carry Forward Leaves (Max 30 days)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaveBalanceScreen() {
    LeaveBalanceScreen()
}
