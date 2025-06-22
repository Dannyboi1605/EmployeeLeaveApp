package com.example.groupassignment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class EmployeeLeaveBalance(
    val name: String,
    val role: String,
    val leaveBalance: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveBalanceScreen(onBack: () -> Unit = {}) {
    // Hardcoded employees
    val allEmployees = listOf(
        EmployeeLeaveBalance("Ahmad", "Manager", 20),
        EmployeeLeaveBalance("Melvin", "Admin", 23),
        EmployeeLeaveBalance("Aisyah", "Employee", 25)
    )

    var searchQuery by remember { mutableStateOf("") }
    var showSuggestions by remember { mutableStateOf(false) }

    val suggestions = allEmployees.filter {
        it.name.contains(searchQuery, ignoreCase = true) && searchQuery.isNotBlank()
    }

    val filteredEmployees = allEmployees.filter {
        it.name.equals(searchQuery, ignoreCase = true)
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    showSuggestions = it.isNotBlank()
                },
                label = { Text("Search Employee Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Suggestions dropdown
            if (showSuggestions && suggestions.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    suggestions.forEach { suggestion ->
                        DropdownMenuItem(
                            text = { Text(suggestion.name) },
                            onClick = {
                                searchQuery = suggestion.name
                                showSuggestions = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Result section
            if (filteredEmployees.isNotEmpty()) {
                LazyColumn {
                    items(filteredEmployees) { employee ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Name: ${employee.name}", style = MaterialTheme.typography.titleMedium)
                                Text("Role: ${employee.role}")
                                Text("Leave Balance: ${employee.leaveBalance} days")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaveBalanceScreen() {
    LeaveBalanceScreen()
}
