package com.example.testkmp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val id: Long? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val name: String,
    val description: String? = null
)