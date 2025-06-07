// File: app/src/main/java/com/example/autoacceptserviceapp/plugins/TestIOPlugin.kt
package com.example.autoacceptserviceapp.plugins

import android.util.Log
import com.example.autoacceptserviceapp.PluginModule

class TestIOPlugin : PluginModule {
    override fun initPlugin() {
        Log.d("TestIOPlugin", "Plugin TestIO khởi tạo")
    }

    override fun executeAction(data: String) {
        Log.d("TestIOPlugin", "Thực hiện hành động với data: $data")
    }
}