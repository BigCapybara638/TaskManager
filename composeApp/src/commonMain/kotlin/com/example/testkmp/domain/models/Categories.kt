package com.example.testkmp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val id: String,
    val name: String,
    val description: String
)