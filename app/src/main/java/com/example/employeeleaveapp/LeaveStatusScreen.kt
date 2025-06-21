package com.example.employeeleaveapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveStatusScreen(
    employeeName: String,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).leaveApplicationDao() }
    val viewModel: LeaveApprovalViewModel = viewModel(
        factory = LeaveApprovalViewModelFactory(dao)
    )

    val applications by viewModel.applications.collectAsState()

    val userApplications = applications.filter { it.employeeName == employeeName }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Leave Status") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(userApplications) { app ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Leave Type: ${app.leaveType}")
                        Text("From: ${app.fromDate} to ${app.toDate}")
                        Text("Reason: ${app.reason}")
                        Text("Status: ${app.status}")
                        if (app.status != "Pending") {
                            Text("Manager Comment: ${app.managerComment}")
                        }
                    }
                }
            }

            if (userApplications.isEmpty()) {
                item {
                    Text(
                        text = "You have not applied for any leaves yet.",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}
