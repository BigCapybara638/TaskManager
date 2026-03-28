package com.example.testkmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GigachatAccessTokenResponse(
    val access_token: String,
    val expires_at: Long
)