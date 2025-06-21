package com.example.employeeleaveapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var currentScreen by remember { mutableStateOf("menu") }

                when (currentScreen) {
                    "menu" -> MainMenuScreen(
                        onNavigateToLeaveType = { currentScreen = "leaveType" },
                        onNavigateToLeaveBalance = { currentScreen = "leaveBalance" }
                    )
                    "leaveType" -> LeaveTypeManagementScreen(onBack = { currentScreen = "menu" })
                    "leaveBalance" -> LeaveBalanceScreen(onBack = { currentScreen = "menu" })
                }
            }
        }
    }
}
