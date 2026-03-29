package com.example.testkmp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Long? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val name: String,
    val description: String? = null,
    @SerialName("category_id")
    val category_id: Long,
    val user_id: String,
    var completed: Boolean = false
)