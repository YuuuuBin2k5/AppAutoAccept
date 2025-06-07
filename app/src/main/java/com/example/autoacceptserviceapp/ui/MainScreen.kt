// File: app/src/main/java/com/example/autoacceptserviceapp/ui/MainScreen.kt
package com.example.autoacceptserviceapp.ui

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.autoacceptserviceapp.AdaptiveTimingManager
import com.example.autoacceptserviceapp.ui.Screen

@Composable
fun MainScreen(onNavigateToSettings: () -> Unit, onNavigateToTracking: () -> Unit, onNavigateToDashboard: () -> Unit) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    // Giá trị tùy chỉnh (tải từ SharedPreferences)
    var minDelay by remember { mutableStateOf(prefs.getLong("min_delay", 1000)) }
    var maxDelay by remember { mutableStateOf(prefs.getLong("max_delay", 20000)) }
    var inactiveStartHour by remember { mutableStateOf(prefs.getInt("inactive_start_hour", 2)) }
    var inactiveEndHour by remember { mutableStateOf(prefs.getInt("inactive_end_hour", 6)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ứng dụng tự động nhận lời mời", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Cấp quyền theo dõi thông báo
        Button(
            onClick = {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Cấp quyền theo dõi thông báo") }
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Chọn ứng dụng cần theo dõi
        Button(
            onClick = onNavigateToSettings,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Chọn ứng dụng cần theo dõi") }
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Xem Tracking Logs
        Button(
            onClick = onNavigateToTracking,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Xem Tracking Logs") }
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Dashboard Phân Tích
        Button(
            onClick = onNavigateToDashboard,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Dashboard Phân Tích") }
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Quản lý Accessibility
        Button(
            onClick = {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Quản lý Accessibility") }
        Spacer(modifier = Modifier.height(16.dp))

        // ✨ **TÙY CHỈNH KHOẢNG THỜI GIAN CHỜ**
        Text("Thời gian chờ (giây): ${minDelay / 1000} - ${maxDelay / 1000}")
        Slider(
            valueRange = 1f..20f,  // ✅ Sửa lại khoảng từ 1 đến 20 giây
            value = (minDelay / 1000).toFloat(),
            onValueChange = {
                minDelay = (it * 1000).toLong()
                maxDelay = (it * 1000 + 5000).toLong() // Đảm bảo maxDelay > minDelay
                prefs.edit().putLong("min_delay", minDelay).apply()
                prefs.edit().putLong("max_delay", maxDelay).apply()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // ⏳ **TÙY CHỈNH GIỜ KHÔNG HOẠT ĐỘNG**
        Text("Giờ không hoạt động: $inactiveStartHour - $inactiveEndHour")
        Row {
            Button(onClick = { if (inactiveStartHour > 0) inactiveStartHour -= 1 }) { Text("-") }
            Text("$inactiveStartHour")
            Button(onClick = { if (inactiveStartHour < inactiveEndHour - 1) inactiveStartHour += 1 }) { Text("+") }
        }
        Row {
            Button(onClick = { if (inactiveEndHour > inactiveStartHour + 1) inactiveEndHour -= 1 }) { Text("-") }
            Text("$inactiveEndHour")
            Button(onClick = { if (inactiveEndHour < 23) inactiveEndHour += 1 }) { Text("+") }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Nút cập nhật
        Button(onClick = { AdaptiveTimingManager.initialize(context) }) { Text("Cập nhật cài đặt") }
    }
}