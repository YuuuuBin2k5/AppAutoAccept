// File: app/src/main/java/com/example/autoacceptserviceapp/ui/AccessibilitySettingsButton.kt
package com.example.autoacceptserviceapp.ui

import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AccessibilitySettingsButton() {
    val context = LocalContext.current
    Button(
        onClick = {
            // Điều hướng người dùng đến trang Settings của Accessibility
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            context.startActivity(intent)
        }
    ) {
        Text("Quản lý Accessibility")
    }
}