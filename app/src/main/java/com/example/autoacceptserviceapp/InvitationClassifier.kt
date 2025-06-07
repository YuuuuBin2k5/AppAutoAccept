// File: app/src/main/java/com/example/autoacceptserviceapp/InvitationClassifier.kt
package com.example.autoacceptserviceapp

import kotlin.random.Random

object InvitationClassifier {
    private val testInvitationTitles = listOf(
        "New Bug Fix Confirmation",
        "New test invitation"
    )
    private const val threshold = 0.7f

    fun classify(title: String, message: String): Float {
        val lowerTitle = title.lowercase()
        val lowerMessage = message.lowercase()
        var score = 0f

        if (testInvitationTitles.any { lowerTitle.contains(it.lowercase()) }) {
            score = 0.9f
        }

        if (lowerMessage.contains("New test") || lowerMessage.contains("bug fix")) {
            score += 0.2f
        }

        return score.coerceAtMost(1f)
    }

    fun isInvitation(title: String, message: String): Boolean {
        // **Mô phỏng hành vi con người: thỉnh thoảng bỏ qua lời mời**
        return classify(title, message) >= threshold && Random.nextBoolean()
    }
}