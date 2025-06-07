// File: app/src/main/java/com/example/autoacceptserviceapp/ui/TrackingStatusScreen.kt
package com.example.autoacceptserviceapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoacceptserviceapp.TrackingLog

@Composable
fun TrackingStatusScreen(onBack: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Tracking Logs", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(TrackingLog.logs) { log ->
                Text(text = log, style = MaterialTheme.typography.bodyMedium)
                Divider()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Quay lại") }
    }
}