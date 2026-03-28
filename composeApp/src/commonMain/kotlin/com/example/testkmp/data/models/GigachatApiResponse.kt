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
    val finish_reason: String
)

@Serializable
data class Message(
    val content: String,
    val role: String
)

@Serializable
data class Usage(
    //@SerialName("prompt_tokens")
    val prompt_tokens: Int,
    //@SerialName("completion_tokens")
    val completion_tokens: Int,
    //@SerialName("total_tokens")
    val total_tokens: Int,
    //@SerialName("precached_prompt_tokens")
    val precached_prompt_tokens: Int? = null // опционально, может отсутствовать
)