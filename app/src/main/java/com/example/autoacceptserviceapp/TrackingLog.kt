// File: app/src/main/java/com/example/autoacceptserviceapp/TrackingLog.kt
package com.example.autoacceptserviceapp

import androidx.compose.runtime.mutableStateListOf

/**
 * Một singleton để lưu trữ danh sách log (tracking events)
 * sử dụng mutableStateListOf để cho phép Compose theo dõi thay đổi.
 */
object TrackingLog {
    val logs = mutableStateListOf<String>()
}