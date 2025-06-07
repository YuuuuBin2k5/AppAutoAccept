// File: app/src/main/java/com/example/autoacceptserviceapp/ui/SettingsScreen.kt
package com.example.autoacceptserviceapp.ui

import android.content.Context.MODE_PRIVATE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_settings", MODE_PRIVATE)
    var selectedApp by remember { mutableStateOf(prefs.getString("selected_app", "") ?: "") }

    // Lấy danh sách ứng dụng do người dùng cài đặt (loại trừ ứng dụng hệ thống)
    val packageManager = context.packageManager
    val installedApps = remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { appInfo -> appInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 }
            .map { it.packageName }
            .sorted()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Chọn ứng dụng cần theo dõi thông báo", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(installedApps) { pkgName ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)) {
                    Text(text = pkgName, modifier = Modifier.weight(1f))
                    Button(onClick = {
                        selectedApp = pkgName
                        prefs.edit().putString("selected_app", pkgName).apply()
                        Toast.makeText(context, "Đã chọn $pkgName", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Chọn")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onBack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Quay lại")
        }
    }
}