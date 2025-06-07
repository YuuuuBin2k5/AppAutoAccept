// File: app/src/main/java/com/example/autoacceptserviceapp/RemoteLogger.kt
package com.example.autoacceptserviceapp

import android.util.Log

object RemoteLogger {
    fun sendLog(logMessage: String) {
        Log.d("RemoteLogger", "Gửi log tới server: $logMessage")
    }
}