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
fun LeaveApprovalScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).leaveApplicationDao() }
    val viewModel: LeaveApprovalViewModel = viewModel( // Changed from :
        factory = LeaveApprovalViewModelFactory(dao)
    )

    val leaveApplications by viewModel.applications.collectAsState()

    var showApproveDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }
    var selectedApp by remember { mutableStateOf<LeaveApplicationEntity?>(null) }
    var selectedComment by remember { mutableStateOf("") }

    // TEST ONLY: Add fake data once
    LaunchedEffect(Unit) {
        if (leaveApplications.isEmpty()) {
            viewModel.addTestData()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leave Approval") },
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
            items(leaveApplications.filter { it.status == "Pending" }) { app ->
                var comment by remember { mutableStateOf("") }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Employee: ${app.employeeName}")
                        Text("Leave Type: ${app.leaveType}")
                        Text("From: ${app.fromDate} to ${app.toDate}")
                        Text("Reason: ${app.reason}")

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = comment,
                            onValueChange = { comment = it },
                            label = { Text("Manager Comment") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                selectedApp = app
                                selectedComment = comment
                                showApproveDialog = true
                            }) {
                                Text("Approve")
                            }

                            Button(onClick = {
                                selectedApp = app
                                selectedComment = comment
                                showRejectDialog = true
                            }) {
                                Text("Reject")
                            }
                        }
                    }
                }
            }
        }

        // Approve Dialog
        if (showApproveDialog && selectedApp != null) {
            AlertDialog(
                onDismissRequest = { showApproveDialog = false },
                title = { Text("Confirm Approval") },
                text = { Text("Are you sure you want to approve this leave?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.approveLeave(selectedApp!!, selectedComment)
                        showApproveDialog = false
                    }) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(onClick = { showApproveDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Reject Dialog
        if (showRejectDialog && selectedApp != null) {
            AlertDialog(
                onDismissRequest = { showRejectDialog = false },
                title = { Text("Confirm Rejection") },
                text = { Text("Are you sure you want to reject this leave?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.rejectLeave(selectedApp!!, selectedComment)
                        showRejectDialog = false
                    }) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(onClick = { showRejectDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
