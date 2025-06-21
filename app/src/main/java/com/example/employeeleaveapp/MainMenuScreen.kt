package com.example.employeeleaveapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigateToLeaveType: () -> Unit,
    onNavigateToLeaveBalance: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Main Menu") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToLeaveType,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Leave Type Management")
            }

            Button(
                onClick = onNavigateToLeaveBalance,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Leave Balance Management")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainMenuScreen() {
    MainMenuScreen(
        onNavigateToLeaveType = {},
        onNavigateToLeaveBalance = {}
    )
}
