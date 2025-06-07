// File: app/src/main/java/com/example/autoacceptserviceapp/ui/AnalyticsDashboardScreen.kt
package com.example.autoacceptserviceapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoacceptserviceapp.TrackingLog

@Composable
fun AnalyticsDashboardScreen(onBack: () -> Unit) {
    // Tính toán số liệu thống kê từ TrackingLog
    val totalNotifications = TrackingLog.logs.size
    val acceptedCount = TrackingLog.logs.count { it.contains("Phát hiện lời mời") }
    val ignoredCount = totalNotifications - acceptedCount

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Dashboard Phân Tích", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Tổng số thông báo: $totalNotifications", style = MaterialTheme.typography.bodyLarge)
        Text("Lời mời được nhận diện: $acceptedCount", style = MaterialTheme.typography.bodyLarge)
        Text("Lời mời bị bỏ qua: $ignoredCount", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Quay lại") }
    }
}