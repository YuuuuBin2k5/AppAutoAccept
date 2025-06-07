// File: app/src/main/java/com/example/autoacceptserviceapp/MyNotificationListener.kt
package com.example.autoacceptserviceapp

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class MyNotificationListener : NotificationListenerService() {
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)
        val selectedApp = prefs.getString("selected_app", null) ?: return

        if (sbn.packageName != selectedApp) return

        val extras = sbn.notification.extras
        val notificationTitle = extras.getCharSequence("android.title")?.toString() ?: ""
        val notificationText = extras.getCharSequence("android.text")?.toString() ?: ""

        val confidence = InvitationClassifier.classify(notificationTitle, notificationText)

        val logMessage = if (confidence >= 0.7f) {
            "Phát hiện lời mời: \"$notificationTitle\" - \"$notificationText\" (score: $confidence)"
        } else {
            "Bỏ qua: \"$notificationTitle\" - \"$notificationText\" (score: $confidence)"
        }
        Log.d("MyNotificationListener", logMessage)

        mainHandler.post {
            TrackingLog.logs.add(0, logMessage)
        }

        if (confidence >= 0.7f && AdaptiveTimingManager.shouldAccept()) {
            val intent = Intent("com.example.autoacceptserviceapp.ACTION_INVITATION_DETECTED").apply {
                putExtra("notification_title", notificationTitle)
                putExtra("notification_text", notificationText)
            }
            sendBroadcast(intent)
        }
    }
}