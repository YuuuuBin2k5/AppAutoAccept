// File: app/src/main/java/com/example/autoacceptserviceapp/PluginModule.kt
package com.example.autoacceptserviceapp

interface PluginModule {
    fun initPlugin()
    fun executeAction(data: String)
}