package com.example.testkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GigachatApiRequest(
    val model: String = "GigaChat-Pro",
    val messages: List<GigaChatMessage>,
    val temperature: Double = 1.0,
    //@SerialName("max_tokens")
    val max_tokens: Int = 500
)

@Serializable
data class GigaChatMessage(
    val role: String,
    val content: String
)