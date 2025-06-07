// File: app/src/main/java/com/example/autoacceptserviceapp/ui/AutoAcceptServiceAppTheme.kt
package com.example.autoacceptserviceapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AutoAcceptServiceAppTheme(content: @Composable () -> Unit) {
    // Sử dụng dynamic color nếu hỗ trợ, ngược lại dùng color scheme tĩnh.
    val context = LocalContext.current
    val colors = try {
        dynamicLightColorScheme(context)
    } catch (ex: Exception) {
        lightColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
            secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5)
        )
    }
    MaterialTheme(
        colorScheme = colors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}