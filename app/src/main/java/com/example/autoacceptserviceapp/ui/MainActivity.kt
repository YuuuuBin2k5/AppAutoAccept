// File: app/src/main/java/com/example/autoacceptserviceapp/MainActivity.kt
package com.example.autoacceptserviceapp.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.autoacceptserviceapp.AdaptiveTimingManager

sealed class Screen {
    object Main : Screen()
    object Settings : Screen()
    object Tracking : Screen()
    object Dashboard : Screen()
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdaptiveTimingManager.initialize(this)
        setContent {
            AutoAcceptServiceAppTheme {
                var currentScreen by remember { androidx.compose.runtime.mutableStateOf<Screen>(
                    Screen.Main
                ) }

                when (currentScreen) {
                    is Screen.Main -> {
                        MainScreen(
                            onNavigateToSettings = { currentScreen = Screen.Settings },
                            onNavigateToTracking = { currentScreen = Screen.Tracking },
                            onNavigateToDashboard = { currentScreen = Screen.Dashboard }
                        )
                    }
                    is Screen.Settings -> {
                        SettingsScreen(onBack = { currentScreen = Screen.Main })
                    }
                    is Screen.Tracking -> {
                        TrackingStatusScreen(onBack = { currentScreen = Screen.Main })
                    }
                    is Screen.Dashboard -> {
                        AnalyticsDashboardScreen(onBack = { currentScreen = Screen.Main })
                    }
                }
            }
        }
    }
}