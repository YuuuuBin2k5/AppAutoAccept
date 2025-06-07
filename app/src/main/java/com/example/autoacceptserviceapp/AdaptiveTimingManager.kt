// File: AdaptiveTimingManager.kt
package com.example.autoacceptserviceapp

import android.content.Context
import java.util.Calendar
import kotlin.random.Random

object AdaptiveTimingManager {
    private var minDelay: Long = 3500
    private var maxDelay: Long = 20000
    private var inactiveStartHour: Int = 2
    private var inactiveEndHour: Int = 6

    fun initialize(context: Context) {
        val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        minDelay = prefs.getLong("min_delay", 3500)
        maxDelay = prefs.getLong("max_delay", 20000)
        inactiveStartHour = prefs.getInt("inactive_start_hour", 2)
        inactiveEndHour = prefs.getInt("inactive_end_hour", 6)
    }

    fun getRandomDelay(): Long {
        return Random.nextLong(minDelay, maxDelay)
    }

    fun shouldAccept(): Boolean {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return !(currentHour in inactiveStartHour..inactiveEndHour)
    }
}