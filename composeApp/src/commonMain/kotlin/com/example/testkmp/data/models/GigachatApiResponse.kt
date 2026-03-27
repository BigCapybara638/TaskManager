package com.example.testkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GigachatApiResponse(
    val choices: List<Choice>,
    val created: Long,
    val model: String,
    val `object`: String,
    val usage: Usage
)

@Serializable
data class Choice(
    val message: Message,
    val index: Int,
    @SerialName("finish_reason")
    val finishReason: String
)

@Serializable
data class Message(
    val content: String,
    val role: String
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int,
    @SerialName("precached_prompt_tokens")
    val precachedPromptTokens: Int? = null // опционально, может отсутствовать
)