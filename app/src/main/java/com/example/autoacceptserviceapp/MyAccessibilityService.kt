// File: app/src/main/java/com/example/autoacceptserviceapp/MyAccessibilityService.kt
package com.example.autoacceptserviceapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import kotlin.random.Random

class MyAccessibilityService : AccessibilityService() {
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onServiceConnected() {
        // Khởi tạo các cài đặt từ AdaptiveTimingManager
        AdaptiveTimingManager.initialize(this)
    }

    // Hàm thực hiện thao tác click với delay và vùng nhấp ngẫu nhiên
    @RequiresApi(Build.VERSION_CODES.N)
    private fun performClickWithRandomDelay(node: AccessibilityNodeInfo) {
        val delay = AdaptiveTimingManager.getRandomDelay()
        mainHandler.postDelayed({
            // Lấy bounding rectangle của nút
            val rect = Rect()
            node.getBoundsInScreen(rect)
            if (rect.isEmpty || rect.width() <= 0 || rect.height() <= 0) {
                // Nếu không lấy được kích thước nút, fallback vào click mặc định
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d("MyAccessibilityService", "Fallback click vì không lấy được bounds")
                return@postDelayed
            }
            // Tính toán vị trí ngẫu nhiên bên trong vùng nút
            val randomX = rect.left + Random.nextInt(rect.width())
            val randomY = rect.top + Random.nextInt(rect.height())
            Log.d("MyAccessibilityService", "Click tại: ($randomX, $randomY) sau $delay ms")

            // Tạo đường dẫn (Path) cho gesture tap
            val path = Path().apply {
                moveTo(randomX.toFloat(), randomY.toFloat())
            }
            // Tạo StrokeDescription cho gesture
            val stroke = GestureDescription.StrokeDescription(path, 0L, 100L)
            val gesture = GestureDescription.Builder().addStroke(stroke).build()
            // Dispatch gesture tap
            dispatchGesture(gesture, null, null)
        }, delay)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("MyAccessibilityService", "Nhận sự kiện: ${event?.toString()}")
        // Kiểm tra xem có nằm trong khung giờ hoạt động không
        if (!AdaptiveTimingManager.shouldAccept()) {
            Log.d("MyAccessibilityService", "Không thực hiện thao tác vì đang trong khung giờ không hoạt động")
            return
        }

        // Lấy rootInActiveWindow
        val rootNode = rootInActiveWindow ?: return

        // Tìm nút "Accept"
        val acceptNodes = rootNode.findAccessibilityNodeInfosByText("Accept Invitation")
        acceptNodes.forEach { performClickWithRandomDelay(it) }

        // Tìm nút "Start"
        val startNodes = rootNode.findAccessibilityNodeInfosByText("Start")
        startNodes.forEach { performClickWithRandomDelay(it) }
    }

    override fun onInterrupt() {
        Log.d("MyAccessibilityService", "Service bị gián đoạn")
    }
}